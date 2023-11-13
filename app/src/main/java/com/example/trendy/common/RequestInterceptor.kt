package com.example.trendy.common

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    private val token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3ZDYwNTNhYmIyYWQyYTkxMmQwMmEzMjUwN2FjMGRjNCIsInN1YiI6IjY0YTQyNWQ5ZTlkYTY5MDEwMTQ3YTBhZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.HHyyVCOJv4F4u-jml5LX4E3un5mSHuQq2G2yqFFu5I4"
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder().apply {
            addHeader("accept", "application/json")
            addHeader("Authorization", "Bearer $token")
        }
        return  chain.proceed(requestBuilder.build())
    }
}