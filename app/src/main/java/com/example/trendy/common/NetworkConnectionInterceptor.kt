package com.example.trendy.common

import com.example.trendy.common.connectivity_checker.ConnectivityChecker
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(
    private val connectivityChecker: ConnectivityChecker
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!connectivityChecker.isOnLine()) {
            throw NoConnectivityException
        }
        return  chain.proceed(chain.request())
    }
}