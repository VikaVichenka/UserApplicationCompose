package com.vikayarska.kotlinapplicationcompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.vikayarska.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private var userRepository: UserRepository
) : ViewModel()