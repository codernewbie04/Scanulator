package com.akmalmf.scanulator.core.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 2023/09/13 00:13
 * akmalmf007@gmail.com
 */
data class ParsedResults(
    @SerializedName("TextOverlay") var TextOverlay: TextOverlay? = TextOverlay(),
    @SerializedName("TextOrientation") var TextOrientation: String? = null,
    @SerializedName("FileParseExitCode") var FileParseExitCode: Int? = null,
    @SerializedName("ParsedText") var ParsedText: String? = null,
    @SerializedName("ErrorMessage") var ErrorMessage: String? = null,
    @SerializedName("ErrorDetails") var ErrorDetails: String? = null
)
