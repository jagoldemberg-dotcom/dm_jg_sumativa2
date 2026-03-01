package com.example.semana1pv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semana1pv.data.firebase.FirebaseAuthService
import com.example.semana1pv.data.firebase.UserProfile
import com.example.semana1pv.data.firebase.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data class Error(val message: String) : AuthState()
    data object Success : AuthState()
}

class AuthViewModel(
    private val authService: FirebaseAuthService = FirebaseAuthService(),
    private val userRepo: UserRepository = UserRepository()
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state

    fun isLoggedIn(): Boolean = authService.currentUser != null

    fun login(email: String, password: String, onOk: () -> Unit) {
        _state.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val user = authService.login(email, password)
                if (user != null) {
                    userRepo.upsert(
                        UserProfile(
                            uid = user.uid,
                            email = user.email ?: email,
                            displayName = user.displayName ?: ""
                        )
                    )
                }
                _state.value = AuthState.Success
                onOk()
            } catch (e: Exception) {
                _state.value = AuthState.Error(e.message ?: "Error de login")
            }
        }
    }

    fun register(email: String, password: String, name: String, onOk: () -> Unit) {
        _state.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val user = authService.register(email, password)
                if (user != null) {
                    userRepo.upsert(
                        UserProfile(
                            uid = user.uid,
                            email = user.email ?: email,
                            displayName = name
                        )
                    )
                }
                _state.value = AuthState.Success
                onOk()
            } catch (e: Exception) {
                _state.value = AuthState.Error(e.message ?: "Error de registro")
            }
        }
    }

    fun sendReset(email: String, onOk: () -> Unit) {
        _state.value = AuthState.Loading
        viewModelScope.launch {
            try {
                authService.sendPasswordReset(email)
                _state.value = AuthState.Success
                onOk()
            } catch (e: Exception) {
                _state.value = AuthState.Error(e.message ?: "Error al enviar correo")
            }
        }
    }

    fun logout() = authService.logout()
}
