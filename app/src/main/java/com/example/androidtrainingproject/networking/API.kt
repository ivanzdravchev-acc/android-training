package com.example.androidtrainingproject.networking

import com.example.androidtrainingproject.models.CategoryModel
import com.example.androidtrainingproject.models.LoginRequestBody
import com.example.androidtrainingproject.models.LoginResponse
import com.example.androidtrainingproject.models.ProductResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @POST(value = "auth/local")
    suspend fun login(@Body body: LoginRequestBody): LoginResponse

    @GET(value = "products")
    suspend fun getProducts(@Query("populate") populate: String) : List<ProductResponse>

    @GET(value = "products/{id}")
    suspend fun getProductById(
        @Path("id") productId: Number,
        @Query("populate") populate: String
    ): ProductResponse

    @GET(value = "categories")
    suspend fun getCategories(): List<CategoryModel>
}