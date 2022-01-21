package com.vikayarska.data.interactor

import com.vikayarska.data.db.user.dao.UserDao
import com.vikayarska.data.mapper.DbToUserMapper
import com.vikayarska.domain.model.BaseResult
import com.vikayarska.domain.usecase.user.GetUserUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserUseCaseImpl @Inject constructor(
    private val userDao: UserDao,
    private val userMapper: DbToUserMapper
) : GetUserUseCase {

    override suspend fun getUser(id: Int) = runCatching { userDao.getUserById(id) }.fold(
        onSuccess = { result ->
            if (result != null) BaseResult.Success(userMapper.map(result)) else BaseResult.Empty()
        },
        onFailure = {
            BaseResult.Error(it.localizedMessage, it as? Exception)
        })
}