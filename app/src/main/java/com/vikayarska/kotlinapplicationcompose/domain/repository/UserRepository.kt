package com.vikayarska.kotlinapplicationcompose.domain.repository


import com.vikayarska.kotlinapplicationcompose.framework.database.dao.UserDao
import com.vikayarska.kotlinapplicationcompose.common.generateRandomDate
import com.vikayarska.kotlinapplicationcompose.network.ApiHelper
import com.vikayarska.kotlinapplicationcompose.network.NetworkResource
import com.vikayarska.kotlinapplicationcompose.domain.datasource.RandomDataSource
import com.vikayarska.kotlinapplicationcompose.domain.model.LoremType
import com.vikayarska.kotlinapplicationcompose.domain.model.NameType
import com.vikayarska.kotlinapplicationcompose.domain.model.TextType
import com.vikayarska.kotlinapplicationcompose.domain.model.User
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

const val introSize = 20

@ActivityRetainedScoped
class UserRepository @Inject constructor(
    private val api: RandomDataSource,
    private val userDao: UserDao
) : ApiHelper() {

    private suspend fun getIntro(): String? {
        return api.getIntro(
            LoremType.Business,
            TextType.Words,
            introSize
        ).body()
    }

    //TODO: align get updated user with id from DB
    suspend fun addUsers(): Flow<NetworkResource<User>> {
        return flow {
            val response = api.getFullNames(
                NameType.FullName,
                USER_QUANTITY
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    it.map { fullName ->
                        val user = User(
                            firstName = fullName.substringBefore(" "),
                            lastName = fullName.substringAfter(" "),
                            birth = Calendar.getInstance().generateRandomDate(),
                            intro = getIntro()
                        )
                        userDao.insert(user)
                        emit(NetworkResource.Success<User>(user))
                    }
                } ?: emit(NetworkResource.Error<User>("Response is empty"))
            } else {
                emit(NetworkResource.Error<User>("${response.code()} -> ${response.message()}"))
            }
        }.flowOn(Dispatchers.IO)
    }

    //TODO: add pagination
    fun getUsers(): Flow<List<User>> {
        return userDao.getAll()
    }

    companion object {
        const val USER_QUANTITY = 30
    }
}