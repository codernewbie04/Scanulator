package com.akmalmf.scanulator.core.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 2023/09/13 00:12
 * akmalmf007@gmail.com
 */
data class ApiResponse(
    @SerializedName("ParsedResults") var ParsedResults: ArrayList<ParsedResults> = arrayListOf(),
    @SerializedName("OCRExitCode") var OCRExitCode: Int,
    @SerializedName("IsErroredOnProcessing") var IsErroredOnProcessing: Boolean,
    @SerializedName("ProcessingTimeInMilliseconds") var ProcessingTimeInMilliseconds: String? = null,
    @SerializedName("SearchablePDFURL") var SearchablePDFURL: String? = null,
    @SerializedName("ErrorMessage") var ErrorMessage: ArrayList<String> = arrayListOf(),

)