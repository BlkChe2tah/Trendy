package com.example.trendy.common.connectivity_checker

import android.net.ConnectivityManager
import com.example.trendy.common.connectivity_checker.ConnectivityChecker
import javax.inject.Inject

class ConnectivityCheckerImpl @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : ConnectivityChecker {

    override fun isOnLine(): Boolean {
        return connectivityManager.activeNetwork != null
    }

}