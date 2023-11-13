package com.example.trendy.common

sealed class ErrorType(
    val code: Int
) {
    object NoConnection : ErrorType(code = 100)
    class RequestFail(code: Int) : ErrorType(code = code)
}