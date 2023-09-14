package com.akmalmf.scanulator.di

import com.akmalmf.scanulator.core.network.ApiService
import com.akmalmf.scanulator.core.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Akmal Muhamad Firdaus on 2023/09/14 14:10
 * akmalmf007@gmail.com
 */
@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesMainRepository(api: ApiService): MainRepository {
        return MainRepository(api)
    }
}