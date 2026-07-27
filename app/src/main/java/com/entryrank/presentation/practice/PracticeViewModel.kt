package com.entryrank.presentation.practice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entryrank.data.local.entity.QuestionEntity
import com.entryrank.data.repository.PracticeRepository
import com.entryrank.data.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val practiceRepository: PracticeRepository,
    private val demoRepository: com.entryrank.data.repository.DemoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val subjectId: String? = savedStateHandle["subjectId"]
    private val topicId: String? = savedStateHandle["topicId"]
    private val bookId: String? = savedStateHandle["bookId"]
    private val chapterId: String? = savedStateHandle["chapterId"]
    private val universityId: String? = savedStateHandle["universityId"]
    
    private val _uiState = MutableStateFlow<PracticeUiState>(PracticeUiState.Loading)
    val uiState: StateFlow<PracticeUiState> = _uiState.asStateFlow()
    
    private var questions: List<QuestionEntity> = emptyList()
    private var currentIndex = 0
    private val userAnswers = mutableMapOf<String, String>()
    private var startTime = System.currentTimeMillis()
    
    init {
        loadQuestions()
    }
    
    private fun loadQuestions() {
        viewModelScope.launch {
            _uiState.value = PracticeUiState.Loading
            
            // Check if demo mode
            if (demoRepository.isDemoMode() && subjectId != null) {
                demoRepository.getDemoQuestions(subjectId, 50).fold(
                    onSuccess = { loadedQuestions ->
                        questions = loadedQuestions
                        updateCurrentQuestion()
                    },
                    onFailure = { error ->
                        _uiState.value = PracticeUiState.Error(
                            error.message ?: "Failed to load demo questions"
                        )
                    }
                )
                return@launch
            }
            
            // Regular mode
            questionRepository.getRandomQuestions(
                count = 50,
                subjectId = subjectId,
                topicId = topicId,
                bookId = bookId,
                chapterId = chapterId,
                universityId = universityId
            ).fold(
                onSuccess = { loadedQuestions ->
                    if (loadedQuestions.isEmpty()) {
                        _uiState.value = PracticeUiState.Error("No questions available")
                    } else {
                        questions = loadedQuestions
                        updateCurrentQuestion()
                    }
                },
                onFailure = { error ->
                    _uiState.value = PracticeUiState.Error(
                        error.message ?: "Failed to load questions"
                    )
                }
            )
        }
    }
    
    private fun updateCurrentQuestion() {
        if (currentIndex < questions.size) {
            val question = questions[currentIndex]
            _uiState.value = PracticeUiState.Question(
                question = question,
                currentNumber = currentIndex + 1,
                totalQuestions = questions.size,
                selectedAnswer = userAnswers[question.id]
            )
        }
    }
    
    fun selectAnswer(answer: String) {
        val currentQuestion = questions.getOrNull(currentIndex) ?: return
        userAnswers[currentQuestion.id] = answer
        updateCurrentQuestion()
    }
    
    fun nextQuestion() {
        if (currentIndex < questions.size - 1) {
            currentIndex++
            updateCurrentQuestion()
        }
    }
    
    fun previousQuestion() {
        if (currentIndex > 0) {
            currentIndex--
            updateCurrentQuestion()
        }
    }
    
    fun goToQuestion(index: Int) {
        if (index in questions.indices) {
            currentIndex = index
            updateCurrentQuestion()
        }
    }
    
    fun submitPractice() {
        viewModelScope.launch {
            _uiState.value = PracticeUiState.Submitting
            
            val timeSpent = ((System.currentTimeMillis() - startTime) / 1000).toInt()
            
            // Check if demo mode
            if (demoRepository.isDemoMode()) {
                submitDemoPractice(timeSpent)
                return@launch
            }
            
            // Regular submission
            practiceRepository.submitPractice(
                questionIds = questions.map { it.id },
                answers = userAnswers,
                timeSpent = timeSpent,
                subjectId = subjectId,
                topicId = topicId,
                bookId = bookId,
                chapterId = chapterId,
                universityId = universityId
            ).fold(
                onSuccess = { result ->
                    _uiState.value = PracticeUiState.Submitted(
                        sessionId = result.session.id,
                        score = result.session.score,
                        correctAnswers = result.session.correctAnswers,
                        totalQuestions = result.session.totalQuestions
                    )
                },
                onFailure = { error ->
                    _uiState.value = PracticeUiState.Error(
                        error.message ?: "Failed to submit practice"
                    )
                }
            )
        }
    }
    
    private suspend fun submitDemoPractice(timeSpent: Int) {
        // Calculate results locally
        var correctCount = 0
        questions.forEach { question ->
            val userAnswer = userAnswers[question.id]
            if (userAnswer == question.correctAnswer) {
                correctCount++
            }
        }
        
        val totalQuestions = questions.size
        val wrongAnswers = userAnswers.size - correctCount
        val skipped = totalQuestions - userAnswers.size
        val score = (correctCount.toFloat() / totalQuestions.toFloat()) * 100
        
        val demoSession = com.entryrank.data.local.entity.PracticeSessionEntity(
            id = "demo-session-${System.currentTimeMillis()}",
            userId = "demo-user-001",
            subjectId = subjectId,
            topicId = topicId,
            bookId = bookId,
            chapterId = chapterId,
            totalQuestions = totalQuestions,
            correctAnswers = correctCount,
            wrongAnswers = wrongAnswers,
            skippedQuestions = skipped,
            score = score,
            timeSpent = timeSpent,
            completedAt = java.time.Instant.now().toString()
        )
        
        demoRepository.saveDemoPracticeSession(demoSession)
        
        _uiState.value = PracticeUiState.Submitted(
            sessionId = demoSession.id,
            score = score,
            correctAnswers = correctCount,
            totalQuestions = totalQuestions
        )
    }
    
    fun getAnsweredCount(): Int = userAnswers.size
    
    fun getProgress(): Float {
        return if (questions.isNotEmpty()) {
            userAnswers.size.toFloat() / questions.size.toFloat()
        } else 0f
    }
}

sealed class PracticeUiState {
    object Loading : PracticeUiState()
    data class Question(
        val question: QuestionEntity,
        val currentNumber: Int,
        val totalQuestions: Int,
        val selectedAnswer: String?
    ) : PracticeUiState()
    object Submitting : PracticeUiState()
    data class Submitted(
        val sessionId: String,
        val score: Float,
        val correctAnswers: Int,
        val totalQuestions: Int
    ) : PracticeUiState()
    data class Error(val message: String) : PracticeUiState()
}
