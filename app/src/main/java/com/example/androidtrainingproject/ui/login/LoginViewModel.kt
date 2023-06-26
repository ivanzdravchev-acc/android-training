package com.example.androidtrainingproject.ui.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtrainingproject.models.LoginRequestBody
import com.example.androidtrainingproject.models.LoginResponse
import com.example.androidtrainingproject.networking.APIClient
import com.example.androidtrainingproject.repository.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: DefaultRepository
): ViewModel() {
    var isLoading by mutableStateOf(false)
    var loginResponse = mutableStateOf(null as LoginResponse?)

    private val _loginFailure = MutableStateFlow<Boolean?>(null)
    val loginFailure: StateFlow<Boolean?> = _loginFailure

    fun login(username: String, password: String, navigateToProductDetails: () -> Unit = {}) {
        _loginFailure.value = null

        viewModelScope.launch {
            try {
                isLoading = true
                val body = LoginRequestBody(username, password)
                val response: LoginResponse = repository.login(body)
                APIClient.setUserJwt(response.jwt)
                loginResponse.value = response
                _loginFailure.value = false
                navigateToProductDetails()
            } catch (e: Exception) {
                _loginFailure.value = true
                Log.e("login error", e.toString())
            } finally {
                isLoading = false
            }
        }
    }
}