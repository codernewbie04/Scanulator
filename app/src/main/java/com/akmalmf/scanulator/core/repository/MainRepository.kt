package com.akmalmf.scanulator.core.repository

import com.akmalmf.scanulator.abstraction.data.RemoteDataSource
import com.akmalmf.scanulator.abstraction.data.Resource
import com.akmalmf.scanulator.core.model.ApiResponse
import com.akmalmf.scanulator.core.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/09/14 13:01
 * akmalmf007@gmail.com
 */
class MainRepository @Inject constructor(
    private val api: ApiService
) : RemoteDataSource() {
    suspend fun scanImage(image: MultipartBody.Part): Flow<Resource<ApiResponse>> = flow {
        emit(Resource.loading())
        emit(safeApiCall {
            api.scanImage(image)
        })
    }.flowOn(Dispatchers.IO)
}