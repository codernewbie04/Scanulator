package com.akmalmf.scanulator.abstraction.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by Akmal Muhamad Firdaus on 2023/09/14 13:04
 * akmalmf007@gmail.com
 */
open class RemoteDataSource {
    suspend inline fun <reified T> safeApiCall(
        crossinline apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiCall.invoke()
                Resource.success(result)
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> HttpExceptionError().parse(throwable)
                    is UnknownHostException -> Resource.error(
                        data = null,
                        message = "No internet connection",
                        code = null,
                        cause = HttpResult.NO_CONNECTION
                    )
                    is SocketTimeoutException -> Resource.error(
                        data = null,
                        message = "Opps... Timeout Connection!",
                        code = null,
                        cause = HttpResult.TIMEOUT
                    )
                    is IOException -> Resource.error(
                        data = null,
                        message = throwable.message.toString(),
                        code = null,
                        cause = HttpResult.BAD_RESPONSE
                    )
                    else -> {
                        Resource.error(
                            data = null,
                            message = throwable.message.toString(),
                            code = null,
                            cause = HttpResult.NOT_DEFINED
                        )
                    }
                }
            }
        }
    }
}