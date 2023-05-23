package com.example.androidtrainingproject.login

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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: DefaultRepository
): ViewModel() {
    var isLoginSuccessful by mutableStateOf<Boolean?>(null)
    var isLoading by mutableStateOf(false)
    var loginResponse = mutableStateOf(null as LoginResponse?)

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val body = LoginRequestBody(username, password)
                val response: LoginResponse = repository.login(body)
                APIClient.setUserJwt(response.jwt)
                loginResponse.value = response;
                isLoginSuccessful = true
            } catch (e: Exception) {
                isLoginSuccessful = false
                Log.e("login error", e.toString())
            } finally {
                isLoading = false
            }
        }
    }
}