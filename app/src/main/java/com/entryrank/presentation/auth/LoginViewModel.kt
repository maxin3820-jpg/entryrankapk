package com.entryrank.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entryrank.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val demoRepository: com.entryrank.data.repository.DemoRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    
    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState.Error("Email and password cannot be empty")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            
            authRepository.login(email, password).fold(
                onSuccess = { user ->
                    _uiState.value = LoginUiState.Success(user.name ?: user.email)
                },
                onFailure = { error ->
                    _uiState.value = LoginUiState.Error(
                        error.message ?: "Login failed. Please check your credentials."
                    )
                }
            )
        }
    }
    
    fun loginAsGuest() {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            demoRepository.enableDemoMode()
            demoRepository.demoLogin("guest@entryrank.com", "").fold(
                onSuccess = { user ->
                    _uiState.value = LoginUiState.Success(user.name ?: "Guest")
                },
                onFailure = { _ ->
                    _uiState.value = LoginUiState.Error("Failed to enter guest mode")
                }
            )
        }
    }
    
    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val userName: String) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
