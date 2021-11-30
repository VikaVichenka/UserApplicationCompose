package com.vikayarska.kotlinapplicationcompose.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.vikayarska.data.db.user.dao.UserDao
import com.vikayarska.data.mapper.mapDBUserToAppUser
import com.vikayarska.data.model.AppUser
import com.vikayarska.domain.model.UpdateResult
import com.vikayarska.domain.usecase.user.UserUseCaseFacade
import com.vikayarska.kotlinapplicationcompose.presentation.model.ViewStateUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val userUseCase: UserUseCaseFacade,
    private val userDao: UserDao,
) : ViewModel() {

    var usersFlow: Flow<PagingData<AppUser>> = Pager(
        config = PagingConfig(
            initialLoadSize = 30,
            pageSize = 15,
            maxSize = 200
        )
    ) {
        userDao.getAllPaging()
    }.flow.map { pagingData ->
        pagingData.map { user -> mapDBUserToAppUser(user) }
    }.cachedIn(viewModelScope)

    private val _viewState = MutableLiveData<ViewStateUpdate>()
    val viewState: LiveData<ViewStateUpdate>
        get() = _viewState

    fun addUsers() = launch {
        _viewState.value = ViewStateUpdate.Loading
        _viewState.value = when (val result = withContext(Dispatchers.IO) {
            userUseCase.addUser()
        }) {
            is UpdateResult.Success, UpdateResult.Empty -> ViewStateUpdate.Completed
            is UpdateResult.Error -> ViewStateUpdate.Failure(result.message)
        }
    }

    fun deleteUsers() = launchIO {
        userUseCase.deleteUser()
    }
}
