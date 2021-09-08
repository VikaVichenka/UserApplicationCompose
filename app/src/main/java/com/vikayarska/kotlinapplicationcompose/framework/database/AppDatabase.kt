package com.vikayarska.kotlinapplicationcompose.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vikayarska.kotlinapplicationcompose.framework.database.converters.DataBaseConverter
import com.vikayarska.kotlinapplicationcompose.framework.database.dao.UserDao
import com.vikayarska.kotlinapplicationcompose.domain.model.User

@Database(entities = [User::class], version = 1)
@TypeConverters(DataBaseConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}