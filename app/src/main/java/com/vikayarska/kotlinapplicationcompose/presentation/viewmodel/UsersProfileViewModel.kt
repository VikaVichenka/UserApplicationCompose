package com.vikayarska.kotlinapplicationcompose.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vikayarska.data.mapper.UserToAppUserMapper
import com.vikayarska.domain.model.BaseResult
import com.vikayarska.domain.usecase.user.GetUserUseCase
import com.vikayarska.kotlinapplicationcompose.presentation.model.ViewState
import dagger.Module
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.Dispatchers

class UsersProfileViewModel @AssistedInject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val userMapper: UserToAppUserMapper,
    @Assisted private val userId: Int?
) : ViewModel() {


    init {
        launchIO { getUser() }
    }

    private val _viewState = MutableLiveData<ViewState>(ViewState.Empty)
    val viewState: LiveData<ViewState>
        get() = _viewState

    private fun getUser() = launch(Dispatchers.Main) {
        userId?.let {
            _viewState.value = ViewState.Loading
            _viewState.value = when (val result = getUserUseCase.getUser(userId)) {
                is BaseResult.Success -> ViewState.Loaded(userMapper.map(result.data))
                is BaseResult.Empty -> ViewState.Empty
                is BaseResult.Error -> ViewState.Failure(result.message)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(userId: Int?): UsersProfileViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            userId: Int?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(userId) as T
            }
        }
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
interface AssistedInjectModule