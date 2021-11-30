package com.vikayarska.domain.usecase.user

import com.vikayarska.domain.model.BaseResult
import com.vikayarska.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserListUseCase {
    suspend operator fun invoke(): Flow<BaseResult<User>>
}