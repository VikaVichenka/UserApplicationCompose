package com.vikayarska.data.interactor

import com.vikayarska.domain.model.BaseResult
import com.vikayarska.domain.model.ResultEnum
import com.vikayarska.domain.repository.UserRepository
import com.vikayarska.domain.usecase.user.AddUsersUseCase
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
) : AddUsersUseCase {

    override suspend fun invoke(): BaseResult<ResultEnum> {
        return coroutineScope {
            when (val userNames = userRepository.getUserNames()) {
                is BaseResult.Success -> {
                    userRepository.addUsers(userNames.data)
                }
                is BaseResult.Error -> BaseResult.Error(userNames.message)
            }
        }
    }
}