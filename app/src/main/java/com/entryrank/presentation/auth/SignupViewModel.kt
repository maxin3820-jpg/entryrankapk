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
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<SignupUiState>(SignupUiState.Idle)
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()
    
    fun signup(name: String, email: String, password: String, confirmPassword: String) {
        // Validation
        when {
            name.isBlank() -> {
                _uiState.value = SignupUiState.Error("Name cannot be empty")
                return
            }
            email.isBlank() -> {
                _uiState.value = SignupUiState.Error("Email cannot be empty")
                return
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _uiState.value = SignupUiState.Error("Invalid email format")
                return
            }
            password.isBlank() -> {
                _uiState.value = SignupUiState.Error("Password cannot be empty")
                return
            }
            password.length < 6 -> {
                _uiState.value = SignupUiState.Error("Password must be at least 6 characters")
                return
            }
            password != confirmPassword -> {
                _uiState.value = SignupUiState.Error("Passwords do not match")
                return
            }
        }
        
        viewModelScope.launch {
            _uiState.value = SignupUiState.Loading
            
            authRepository.signup(email, password, name).fold(
                onSuccess = { user ->
                    _uiState.value = SignupUiState.Success(user.name ?: user.email)
                },
                onFailure = { error ->
                    _uiState.value = SignupUiState.Error(
                        error.message ?: "Signup failed. Please try again."
                    )
                }
            )
        }
    }
    
    fun resetState() {
        _uiState.value = SignupUiState.Idle
    }
}

sealed class SignupUiState {
    object Idle : SignupUiState()
    object Loading : SignupUiState()
    data class Success(val userName: String) : SignupUiState()
    data class Error(val message: String) : SignupUiState()
}
