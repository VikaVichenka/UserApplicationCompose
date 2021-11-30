package com.vikayarska.data.db.user.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vikayarska.data.db.dao.BaseDao
import com.vikayarska.data.db.user.entity.DbUser
import com.vikayarska.data.db.user.entity.UserMinimal
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao : BaseDao<DbUser> {
    @Query("SELECT * FROM users")
    fun getAllPaging(): PagingSource<Int, DbUser>

    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<DbUser>>

    @Query("SELECT * FROM users")
    fun getAllBase(): List<DbUser>

    @Query("SELECT * FROM users WHERE id LIKE :id")
    suspend fun getUserById(id: Int): DbUser

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): Flow<List<DbUser>>

    @Query("SELECT id, first_name, last_name FROM users")
    fun getUsersMinimal(): Flow<List<UserMinimal>>

    @Query("SELECT * FROM users WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): DbUser

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuspend(obj: List<DbUser>)

    @Query("DELETE FROM users")
    fun deleteAll()
}