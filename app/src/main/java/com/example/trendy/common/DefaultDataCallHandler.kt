package com.example.trendy.common

import arrow.core.Either
import arrow.core.left
import arrow.core.right

suspend fun <T>defaultDataCallHandler(
    request: suspend () -> T,
    catch: ((e: Exception) -> ErrorType?)? = null
) : Either<ErrorType, T> {
    return try {
        request.invoke().right()
    } catch (e: Exception) {
        catch?.invoke(e)?.left() ?: throw e
//        when(e) {
//            else -> {
//                catch?.invoke(e)?.left() ?: throw e
//            }
//        }
    }
}

suspend fun <T>defaultNetworkCallHandler(
    request: suspend () -> T,
    catch: ((e: Exception) -> ErrorType?)? = null
) : Either<ErrorType, T> {
    return try {
        request.invoke().right()
    } catch (e: Exception) {
        when(e) {
            is NoConnectivityException -> ErrorType.NoConnection.left()
            is ApiRequestFailException -> ErrorType.RequestFail(e.code).left()
            else -> {
                catch?.invoke(e)?.left() ?: throw e
            }
        }
    }
}