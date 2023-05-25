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
    var loginFailure by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var loginResponse = mutableStateOf(null as LoginResponse?)

    fun login(username: String, password: String, navigateToProductDetails: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                isLoading = true
                val body = LoginRequestBody(username, password)
                val response: LoginResponse = repository.login(body)
                APIClient.setUserJwt(response.jwt)
                loginResponse.value = response
                navigateToProductDetails()
            } catch (e: Exception) {
                loginFailure = true
                Log.e("login error", e.toString())
            } finally {
                isLoading = false
            }
        }
    }
}