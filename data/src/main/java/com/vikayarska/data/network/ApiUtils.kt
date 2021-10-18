package com.vikayarska.data.network

import com.vikayarska.domain.model.BaseResult
import retrofit2.Response

const val ERROR_EMPTY_RESPONSE = "Response is empty"

fun <T> Response<T>.getResponse(): BaseResult<T> {
    if (this.isSuccessful) {
        this.body()?.let { return BaseResult.Success(it) }
        return BaseResult.Error(ERROR_EMPTY_RESPONSE)
    }
    return BaseResult.Error("${this.code()} -> ${this.message()}")
}

