package com.entryrank.data.repository

import androidx.paging.*
import com.entryrank.data.local.dao.QuestionDao
import com.entryrank.data.local.entity.QuestionEntity
import com.entryrank.data.remote.api.QuestionApi
import com.entryrank.data.remote.dto.QuestionDto
import com.entryrank.data.remote.dto.QuestionFilterRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionRepository @Inject constructor(
    private val questionApi: QuestionApi,
    private val questionDao: QuestionDao,
    private val authRepository: AuthRepository
) {
    
    fun getQuestionsPaged(
        subjectId: String? = null,
        topicId: String? = null,
        bookId: String? = null,
        chapterId: String? = null
    ): Flow<PagingData<QuestionEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                questionDao.getQuestionsPaged(subjectId, topicId, bookId, chapterId)
            }
        ).flow
    }
    
    suspend fun getRandomQuestions(
        count: Int,
        subjectId: String? = null,
        topicId: String? = null,
        bookId: String? = null,
        chapterId: String? = null,
        universityId: String? = null,
        difficulty: Int? = null,
        excludeIds: List<String>? = null
    ): Result<List<QuestionEntity>> {
        return try {
            // Try local first if we have enough questions
            val localQuestions = questionDao.getRandomQuestions(
                count, subjectId, topicId, bookId, chapterId
            )
            
            if (localQuestions.size >= count) {
                return Result.success(localQuestions)
            }
            
            // Fetch from API
            val token = authRepository.getToken().first()
                ?: return Result.failure(Exception("No token"))
            
            val request = QuestionFilterRequest(
                subjectId = subjectId,
                topicId = topicId,
                bookId = bookId,
                chapterId = chapterId,
                universityId = universityId,
                difficulty = difficulty,
                limit = count,
                excludeIds = excludeIds
            )
            
            val questionsDto = questionApi.getRandomQuestions("Bearer $token", request)
            
            // Save to local database
            val questions = questionsDto.map { dto ->
                QuestionEntity(
                    id = dto.id,
                    question = dto.question,
                    optionA = dto.optionA,
                    optionB = dto.optionB,
                    optionC = dto.optionC,
                    optionD = dto.optionD,
                    correctAnswer = dto.correctAnswer,
                    explanation = dto.explanation,
                    subjectId = dto.subjectId,
                    topicId = dto.topicId,
                    bookId = dto.bookId,
                    chapterId = dto.chapterId,
                    universityId = dto.universityId,
                    difficulty = dto.difficulty,
                    createdAt = dto.createdAt
                )
            }
            
            questionDao.insertQuestions(questions)
            
            Result.success(questions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun syncQuestions(
        subjectId: String? = null,
        bookId: String? = null,
        chapterId: String? = null
    ): Result<Int> {
        return try {
            val token = authRepository.getToken().first()
                ?: return Result.failure(Exception("No token"))
            
            var offset = 0
            val limit = 100
            var totalSynced = 0
            
            while (true) {
                val questionsDto = questionApi.getQuestions(
                    token = "Bearer $token",
                    subjectId = subjectId,
                    bookId = bookId,
                    chapterId = chapterId,
                    limit = limit,
                    offset = offset
                )
                
                if (questionsDto.isEmpty()) break
                
                val questions = questionsDto.map { dto ->
                    QuestionEntity(
                        id = dto.id,
                        question = dto.question,
                        optionA = dto.optionA,
                        optionB = dto.optionB,
                        optionC = dto.optionC,
                        optionD = dto.optionD,
                        correctAnswer = dto.correctAnswer,
                        explanation = dto.explanation,
                        subjectId = dto.subjectId,
                        topicId = dto.topicId,
                        bookId = dto.bookId,
                        chapterId = dto.chapterId,
                        universityId = dto.universityId,
                        difficulty = dto.difficulty,
                        createdAt = dto.createdAt
                    )
                }
                
                questionDao.insertQuestions(questions)
                totalSynced += questions.size
                
                offset += limit
            }
            
            Result.success(totalSynced)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getQuestionById(questionId: String): Result<QuestionEntity> {
        return try {
            // Try local first
            val localQuestion = questionDao.getQuestionById(questionId)
            if (localQuestion != null) {
                return Result.success(localQuestion)
            }
            
            // Fetch from API
            val token = authRepository.getToken().first()
                ?: return Result.failure(Exception("No token"))
            
            val dto = questionApi.getQuestionById("Bearer $token", questionId)
            val question = QuestionEntity(
                id = dto.id,
                question = dto.question,
                optionA = dto.optionA,
                optionB = dto.optionB,
                optionC = dto.optionC,
                optionD = dto.optionD,
                correctAnswer = dto.correctAnswer,
                explanation = dto.explanation,
                subjectId = dto.subjectId,
                topicId = dto.topicId,
                bookId = dto.bookId,
                chapterId = dto.chapterId,
                universityId = dto.universityId,
                difficulty = dto.difficulty,
                createdAt = dto.createdAt
            )
            
            questionDao.insertQuestions(listOf(question))
            
            Result.success(question)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun searchQuestions(query: String): Flow<List<QuestionEntity>> {
        return questionDao.searchQuestions("%$query%")
    }
}
