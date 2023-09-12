package com.akmalmf.scanulator.core.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 2023/09/13 00:13
 * akmalmf007@gmail.com
 */
data class TextOverlay(
    @SerializedName("Lines") var Lines: ArrayList<Lines> = arrayListOf(),
    @SerializedName("HasOverlay") var HasOverlay: Boolean? = null
)
