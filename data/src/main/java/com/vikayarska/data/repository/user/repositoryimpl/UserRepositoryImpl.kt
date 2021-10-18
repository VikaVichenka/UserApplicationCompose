package com.vikayarska.data.repository.user.repositoryimpl

import com.vikayarska.data.datasource.RandomDataSource
import com.vikayarska.data.db.user.dao.UserDao
import com.vikayarska.data.mapper.UserMappersFacade
import com.vikayarska.data.mapper.mapList
import com.vikayarska.data.mapper.mapNetworkUserToDBUser
import com.vikayarska.data.network.getResponse
import com.vikayarska.data.network.model.LoremType
import com.vikayarska.data.network.model.NameType
import com.vikayarska.data.network.model.NetworkUser
import com.vikayarska.data.network.model.TextType
import com.vikayarska.domain.model.BaseResult
import com.vikayarska.domain.model.ResultEnum
import com.vikayarska.domain.model.User
import com.vikayarska.domain.repository.ImageRepository
import com.vikayarska.domain.repository.UserRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val api: RandomDataSource,
    private val imageRepository: ImageRepository,
    private val userDao: UserDao,
    private val userMappersFacade: UserMappersFacade
) : UserRepository {

    override suspend fun getIntro(): String? {
        return api.getIntro(
            LoremType.Normal,
            TextType.Words,
            INTRO_LENGTH
        ).body()
    }

    override suspend fun addUsers(userNames: List<String>): BaseResult<ResultEnum> {
        return coroutineScope {
            val users = userNames.map { fullName ->
                val intro = getIntro()
                val image = when (val imageBase = imageRepository.getImage()) {
                    is BaseResult.Success -> imageBase.data
                    is BaseResult.Error -> null
                }
                userMappersFacade.mapNetworkToDbUser(NetworkUser(fullName, intro, image))
            }
            userDao.insertSuspend(users)
            BaseResult.Success(ResultEnum.Success)
        }
    }

    override suspend fun getUserNames(): BaseResult<List<String>> {
        return api.getFullNames(
            NameType.FullName,
            USER_QUANTITY
        ).getResponse()
    }

//TODO: align without PagingData

//   override suspend fun getUsers(): Flow<PagingData<User>> {
//        val myPagingConfig = PagingConfig(
//            initialLoadSize = 60,
//            pageSize = 30,
//            maxSize = 200
//        )
//
//        return Pager(config = myPagingConfig) {
//            userDao.getAllPaging()
//        }.flow.map { pagingData ->
//            pagingData.map { user -> mapDBUser(user) }
//        }
//    }

    override suspend fun deleteUsers() {
        userDao.deleteAll()
    }

    companion object {
        const val USER_QUANTITY = 30
        const val INTRO_LENGTH = 20
    }
}