package com.vikayarska.kotlinapplicationcompose.framework.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.vikayarska.kotlinapplicationcompose.domain.model.User
import com.vikayarska.kotlinapplicationcompose.domain.model.UserMinimal
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao : BaseDao<User> {
    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): Flow<List<User>>

    @Query("SELECT id, first_name, last_name FROM users")
    fun getUsersMinimal(): Flow<List<UserMinimal>>

    @Query("SELECT * FROM users WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User
}