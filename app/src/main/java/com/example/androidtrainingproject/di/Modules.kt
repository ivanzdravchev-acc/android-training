package com.example.androidtrainingproject.di

import com.example.androidtrainingproject.networking.API
import com.example.androidtrainingproject.networking.APIClient
import com.example.androidtrainingproject.repository.DefaultRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideApiService(): API {
        return APIClient().defaultService
    }

    @Singleton
    @Provides
    fun providesPostRepository(apiService: API) = DefaultRepository(apiService)
}