package com.akmalmf.scanulator.di

import com.akmalmf.scanulator.core.network.ApiConfig
import com.akmalmf.scanulator.core.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Akmal Muhamad Firdaus on 2023/09/14 14:11
 * akmalmf007@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun providesApiService(): ApiService {
        return ApiConfig.getApiService()
    }
}