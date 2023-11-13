package com.example.trendy.common

import java.io.IOException

object NoConnectivityException : IOException()

class ApiRequestFailException(val code: Int) : IOException()