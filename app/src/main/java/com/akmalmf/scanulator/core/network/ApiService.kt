package com.akmalmf.scanulator.core.network

import com.akmalmf.scanulator.core.model.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Created by Akmal Muhamad Firdaus on 2023/09/12 23:28
 * akmalmf007@gmail.com
 */
interface ApiService {
    @Multipart
    @POST("parse/image")
    suspend fun scanImage(
        @Part image: MultipartBody.Part,
        @Part("OCREngine") ocrEngine: RequestBody = RequestBody.create(null, "2")
    ): ApiResponse
}