package com.vikayarska.kotlinapplicationcompose.network

import retrofit2.Response

abstract class ApiHelper {
    suspend fun <T> callApi(apiCall: suspend () -> Response<T>): NetworkResource<T> {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let { return NetworkResource.Success(it) }
            return NetworkResource.Error(ERROR_EMPTY_RESPONSE)
        }
        return NetworkResource.Error("${response.code()} -> ${response.message()}")
    }

    companion object {
        const val ERROR_EMPTY_RESPONSE = "Response is empty"
    }
}