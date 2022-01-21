package com.vikayarska.domain.usecase.user

import com.vikayarska.domain.model.BaseResult
import com.vikayarska.domain.model.User

interface GetUserUseCase {
    suspend fun getUser(id: Int): BaseResult<User>
}