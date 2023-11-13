package com.example.trendy.common

import okhttp3.Interceptor
import okhttp3.Response

class ApiResponseErrorHandlingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val result = chain.proceed(chain.request())
        if (!result.isSuccessful) {
            throw ApiRequestFailException(result.code)
        }
        return result
    }
}