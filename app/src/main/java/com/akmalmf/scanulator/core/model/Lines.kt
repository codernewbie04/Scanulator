package com.akmalmf.scanulator.core.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 2023/09/13 00:14
 * akmalmf007@gmail.com
 */
data class Lines(
    @SerializedName("LineText") var LineText: String? = null,
    @SerializedName("Words") var Words: ArrayList<Words> = arrayListOf(),
    @SerializedName("MaxHeight") var MaxHeight: Int? = null,
    @SerializedName("MinTop") var MinTop: Int? = null
)
