package com.vikayarska.kotlinapplicationcompose.presentation.model

sealed class ViewState {
    object Loading : ViewState()
    data class Loaded<T>(val data: T) : ViewState()
    data class Failure(val errorMessage: String) : ViewState()
}