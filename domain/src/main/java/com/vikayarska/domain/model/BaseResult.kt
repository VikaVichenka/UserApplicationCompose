package com.vikayarska.domain.model

import java.lang.Exception

sealed class BaseResult<T> {
    class Success<T>(val data: T) : BaseResult<T>()
    class Error<T>(val message: String, val exception: Exception? = null) : BaseResult<T>()
}

enum class ResultEnum {
    Success,
    Error
}