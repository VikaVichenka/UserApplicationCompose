package com.vikayarska.domain.usecase.user

import com.vikayarska.domain.model.UpdateResult

interface DeleteUserUseCase {
    suspend operator fun invoke() : UpdateResult
}