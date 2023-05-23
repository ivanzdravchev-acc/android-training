package com.example.androidtrainingproject.networking

import com.example.androidtrainingproject.models.LoginRequestBody
import com.example.androidtrainingproject.models.LoginResponse
import com.example.androidtrainingproject.models.ProductResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface API {
    @POST(value = "auth/local")
    suspend fun login(@Body body: LoginRequestBody): LoginResponse

    @GET(value = "products/{id}?populate=*")
    suspend fun getProductById(@Path("id") productId: Number): ProductResponse
}