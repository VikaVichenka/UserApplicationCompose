package com.vikayarska.domain.usecase.user

import com.vikayarska.domain.model.BaseResult
import com.vikayarska.domain.model.ResultEnum

interface AddUsersUseCase {
    suspend operator fun invoke(): BaseResult<ResultEnum>
}