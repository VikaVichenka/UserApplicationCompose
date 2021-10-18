package com.vikayarska.data.interactor

import com.vikayarska.domain.model.BaseResult
import com.vikayarska.domain.model.ResultEnum
import com.vikayarska.domain.repository.UserRepository
import com.vikayarska.domain.usecase.user.DeleteUserUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
) : DeleteUserUseCase {
    override suspend fun invoke() = userRepository.deleteUsers()
}