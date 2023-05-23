package com.example.androidtrainingproject.repository

import com.example.androidtrainingproject.models.LoginRequestBody
import com.example.androidtrainingproject.models.LoginResponse
import com.example.androidtrainingproject.models.ProductResponse
import com.example.androidtrainingproject.networking.API

class DefaultRepository(private val apiService: API): ApiHelper {
    override suspend fun login(body: LoginRequestBody): LoginResponse = apiService.login(body)

    override suspend fun getProductById(id: Number): ProductResponse = apiService.getProductById((id))
}