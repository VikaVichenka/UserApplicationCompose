package com.vikayarska.domain.repository

import com.vikayarska.domain.model.BaseResult

interface UserRepository {

    suspend fun getIntro(): String?

    suspend fun addUsers(userNames: List<String>)

    suspend fun getUserNames(): BaseResult<List<String>>

    suspend fun deleteUsers()
}