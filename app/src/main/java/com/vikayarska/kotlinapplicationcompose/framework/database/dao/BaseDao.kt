package com.vikayarska.kotlinapplicationcompose.framework.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import io.reactivex.rxjava3.core.Single

interface BaseDao<T> {
    @Insert
    fun insert(obj: T)

    @Insert
    fun insert(vararg obj: T)

    @Insert
    fun insert(obj: List<T>)

    @Update
    fun update(obj: T)

    @Delete
    fun delete(obj: T)
}