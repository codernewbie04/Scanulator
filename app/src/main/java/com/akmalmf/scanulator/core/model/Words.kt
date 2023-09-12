package com.akmalmf.scanulator.core.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 2023/09/13 00:14
 * akmalmf007@gmail.com
 */
data class Words(
    @SerializedName("WordText") var WordText: String? = null,
    @SerializedName("Left") var Left: Int? = null,
    @SerializedName("Top") var Top: Int? = null,
    @SerializedName("Height") var Height: Int? = null,
    @SerializedName("Width") var Width: Int? = null
)
