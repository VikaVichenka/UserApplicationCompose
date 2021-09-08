package com.vikayarska.kotlinapplicationcompose.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikayarska.kotlinapplicationcompose.domain.model.User
import com.vikayarska.kotlinapplicationcompose.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    init {
        viewModelScope.launch {
            userRepository.getUsers().collect { value ->
                _users.value = value
            }
        }
    }

    fun addUsers() {
        viewModelScope.launch {
            userRepository.getUsers().collect { value ->
                _users.value = value
            }
        }
    }
}
