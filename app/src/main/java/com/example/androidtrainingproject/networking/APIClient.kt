package com.example.androidtrainingproject.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {
    companion object {
        var jwt = ""

        fun setUserJwt(newJwt: String) {
            this.jwt = newJwt
        }

        fun clearJwt() {
            this.jwt = ""
        }
    }
    private val DEFAULT_TIMEOUT = 1L

    val defaultService: API

    init {
        defaultService = createService()
    }

    // User and auth service
    private fun createService(): API {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MINUTES)
            .addInterceptor {chain ->
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()

                if (jwt != "") {
                    requestBuilder.addHeader("Authorization", "Bearer $jwt")
                }

                chain.proceed(requestBuilder.build())
            }
            .build()

        val client = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://ethereal-artefacts.fly.dev/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return client.create(API::class.java)
    }
}