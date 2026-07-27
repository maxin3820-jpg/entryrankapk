package com.entryrank.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entryrank.data.local.entity.SubjectEntity
import com.entryrank.data.local.entity.UniversityEntity
import com.entryrank.data.local.entity.UserEntity
import com.entryrank.data.repository.AuthRepository
import com.entryrank.data.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val commonRepository: CommonRepository,
    private val demoRepository: com.entryrank.data.repository.DemoRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    init {
        loadDashboard()
    }
    
    private fun loadDashboard() {
        viewModelScope.launch {
            try {
                // Check if demo mode
                if (demoRepository.isDemoMode()) {
                    loadDemoData()
                    return@launch
                }
                
                // Get current user
                val userResult = authRepository.getCurrentUser()
                val user = userResult.getOrNull()
                
                if (user == null) {
                    _uiState.value = DashboardUiState.Error("Failed to load user")
                    return@launch
                }
                
                // Sync data from server
                syncData()
                
                // Collect subjects and universities
                launch {
                    commonRepository.getSubjects().collectLatest { subjects ->
                        commonRepository.getUniversities().collectLatest { universities ->
                            _uiState.value = DashboardUiState.Success(
                                user = user,
                                subjects = subjects,
                                universities = universities
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = DashboardUiState.Error(
                    e.message ?: "Failed to load dashboard"
                )
            }
        }
    }
    
    private fun loadDemoData() {
        viewModelScope.launch {
            try {
                val user = demoRepository.getDemoUser().getOrThrow()
                demoRepository.getDemoSubjects().collectLatest { subjects ->
                    _uiState.value = DashboardUiState.Success(
                        user = user,
                        subjects = subjects,
                        universities = emptyList() // No universities in demo mode
                    )
                }
            } catch (e: Exception) {
                _uiState.value = DashboardUiState.Error("Failed to load demo data")
            }
        }
    }
    
    private suspend fun syncData() {
        try {
            // Sync subjects, universities in background
            launch {
                commonRepository.syncSubjects()
                commonRepository.syncUniversities()
            }
        } catch (e: Exception) {
            // Log error but don't fail - use cached data
        }
    }
    
    fun refresh() {
        loadDashboard()
    }
    
    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _uiState.value = DashboardUiState.LoggedOut
        }
    }
}

sealed class DashboardUiState {
    object Loading : DashboardUiState()
    data class Success(
        val user: UserEntity,
        val subjects: List<SubjectEntity>,
        val universities: List<UniversityEntity>
    ) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
    object LoggedOut : DashboardUiState()
}
