package com.akmalmf.scanulator.abstraction.data

import com.akmalmf.scanulator.core.model.ErrorModel

/**
 * Created by Akmal Muhamad Firdaus on 2023/09/14 13:05
 * akmalmf007@gmail.com
 */
data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val code: Int?,
    val cause: HttpResult = HttpResult.NOT_DEFINED,
    val errorData: ErrorModel?
) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(
            status = Status.SUCCESS,
            data = data,
            message = null,
            code = null,
            errorData = null
        )

        fun <T> error(
            data: T? = null,
            message: String? = null,
            code: Int? = null,
            cause: HttpResult = HttpResult.NOT_DEFINED,
            error: ErrorModel? = null
        ): Resource<T> = Resource(
            status = Status.ERROR,
            data = data,
            message = message,
            code = code,
            cause = cause,
            errorData = error
        )

        fun <T> loading(data: T? = null): Resource<T> =
            Resource(
                status = Status.LOADING,
                data = data,
                message = null,
                code = null,
                errorData = null
            )
    }
}