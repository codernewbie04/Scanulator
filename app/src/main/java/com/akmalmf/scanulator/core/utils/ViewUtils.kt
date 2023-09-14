package com.akmalmf.scanulator.core.utils

import android.view.View

/**
 * Created by Akmal Muhamad Firdaus on 2023/09/14 13:51
 * akmalmf007@gmail.com
 */
fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toInvisible() {
    visibility = View.INVISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}