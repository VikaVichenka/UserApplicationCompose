package com.vikayarska.kotlinapplicationcompose.presentation.model

import com.vikayarska.data.model.AppUser

sealed class ViewState {
    object Loading : ViewState()
    data class Loaded<T>(val data: T) : ViewState()
    object Empty : ViewState()
    data class Failure(val errorMessage: String?) : ViewState()
}

data class UserProfileViewState(val user: AppUser, var isLoading: Boolean = false)
sealed class ViewStateUpdate {
    object Loading : ViewStateUpdate()
    object Completed : ViewStateUpdate()
    data class Failure(val errorMessage: String?) : ViewStateUpdate()
}