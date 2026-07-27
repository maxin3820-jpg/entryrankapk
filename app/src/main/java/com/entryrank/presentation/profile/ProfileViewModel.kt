package com.entryrank.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entryrank.data.local.entity.ProgressEntity
import com.entryrank.data.local.entity.UserEntity
import com.entryrank.data.repository.AuthRepository
import com.entryrank.data.repository.PracticeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val practiceRepository: PracticeRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    
    init {
        loadProfile()
    }
    
    private fun loadProfile() {
        viewModelScope.launch {
            try {
                // Get current user
                val userResult = authRepository.getCurrentUser()
                val user = userResult.getOrNull()
                
                if (user == null) {
                    _uiState.value = ProfileUiState.Error("Failed to load user profile")
                    return@launch
                }
                
                // Get user progress
                practiceRepository.getUserProgress(user.id).collectLatest { progress ->
                    // Calculate total stats
                    val totalQuestionsAttempted = progress.sumOf { it.totalQuestionsAttempted }
                    val totalCorrectAnswers = progress.sumOf { it.correctAnswers }
                    val totalTimeSpent = progress.sumOf { it.timeSpent }
                    val overallAccuracy = if (totalQuestionsAttempted > 0) {
                        (totalCorrectAnswers.toFloat() / totalQuestionsAttempted.toFloat()) * 100
                    } else 0f
                    
                    _uiState.value = ProfileUiState.Success(
                        user = user,
                        progress = progress,
                        totalQuestionsAttempted = totalQuestionsAttempted,
                        totalCorrectAnswers = totalCorrectAnswers,
                        totalTimeSpent = totalTimeSpent,
                        overallAccuracy = overallAccuracy
                    )
                }
                
                // Sync progress in background
                launch {
                    practiceRepository.syncProgress(user.id)
                }
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(
                    e.message ?: "Failed to load profile"
                )
            }
        }
    }
    
    fun refresh() {
        _uiState.value = ProfileUiState.Loading
        loadProfile()
    }
    
    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _uiState.value = ProfileUiState.LoggedOut
        }
    }
}

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(
        val user: UserEntity,
        val progress: List<ProgressEntity>,
        val totalQuestionsAttempted: Int,
        val totalCorrectAnswers: Int,
        val totalTimeSpent: Int,
        val overallAccuracy: Float
    ) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
    object LoggedOut : ProfileUiState()
}
