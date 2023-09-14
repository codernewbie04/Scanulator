package com.akmalmf.scanulator.core.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Akmal Muhamad Firdaus on 2023/09/14 13:09
 * akmalmf007@gmail.com
 */
inline fun <reified T> Gson.fromJson(json:String): T =
    this.fromJson(json,object : TypeToken<T>(){}.type)