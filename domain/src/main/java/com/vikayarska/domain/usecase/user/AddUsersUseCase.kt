package com.vikayarska.domain.usecase.user

import com.vikayarska.domain.model.UpdateResult

interface AddUsersUseCase {
    suspend operator fun invoke(): UpdateResult
}