package com.training.programmingtest.data.wrapper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val code: Int, val message: String?) : NetworkResult<Nothing>()
    data object Loading : NetworkResult<Nothing>()
    data class Exception(val e: Throwable) : NetworkResult<Nothing>()
}


inline fun <reified T> handleApi(
    crossinline execute: suspend () -> Response<T>
) = flow {
    try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            emit(NetworkResult.Success(body))
        } else {
            emit(NetworkResult.Error(response.code(), response.message()))
        }
    } catch (e: HttpException) {
        emit(NetworkResult.Error(code = e.code(), message = e.message()))
    } catch (e: Throwable) {
        emit(NetworkResult.Exception(e))
    }
}.flowOn(Dispatchers.IO)
