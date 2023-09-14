package com.akmalmf.scanulator.core.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 2023/09/14 13:07
 * akmalmf007@gmail.com
 */
data class ErrorModel(
    @SerializedName("OCRExitCode") var OCRExitCode: Int,
    @SerializedName("IsErroredOnProcessing") var IsErroredOnProcessing: Boolean,
    @SerializedName("ErrorMessage") var ErrorMessage: ArrayList<String> = arrayListOf(),
    @SerializedName("ProcessingTimeInMilliseconds") var ProcessingTimeInMilliseconds: String? = null
)