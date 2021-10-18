package com.vikayarska.kotlinapplicationcompose.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.vikayarska.data.db.user.dao.UserDao
import com.vikayarska.data.mapper.mapDBUser
import com.vikayarska.data.mapper.mapDBUserToAppUser
import com.vikayarska.data.model.AppUser
import com.vikayarska.domain.model.BaseResult
import com.vikayarska.domain.model.ResultEnum
import com.vikayarska.domain.repository.UserRepository
import com.vikayarska.domain.usecase.user.AddUsersUseCase
import com.vikayarska.domain.usecase.user.UserUseCaseFacade
import com.vikayarska.kotlinapplicationcompose.presentation.model.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val userUseCase: UserUseCaseFacade,
    private val userDao: UserDao,
) : ViewModel() {

//    private lateinit var _usersFlow: Flow<PagingData<AppUser>>
//    val usersFlow: Flow<PagingData<AppUser>>
//        get() = _usersFlow
//
//    init {
//        getUsers()
//    }

    val viewState = MutableLiveData<ViewState>()

    fun addUsers() {
        viewState.value = ViewState.Loading
        viewModelScope.launch {
            userUseCase.addUser().let { result ->
                when (result) {
                    is BaseResult.Success -> viewState.value =
                        ViewState.Loaded(ResultEnum.Success)
                    is BaseResult.Error -> viewState.value = ViewState.Failure(result.message)
                }
            }
        }
    }

    //TODO: move to useCase and move mapping to proper class
    fun getUsers(): Flow<PagingData<AppUser>> {
        val myPagingConfig = PagingConfig(
            initialLoadSize = 60,
            pageSize = 30,
            maxSize = 200
        )

        return Pager(config = myPagingConfig) {
            userDao.getAllPaging()
        }.flow.map { pagingData ->
            pagingData.map { user -> mapDBUserToAppUser(user) }
        }.cachedIn(viewModelScope)
    }


    fun deleteUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            userUseCase.deleteUser()
        }
    }
}
