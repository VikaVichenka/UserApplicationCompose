package com.vikayarska.kotlinapplicationcompose.framework.injection

import com.vikayarska.kotlinapplicationcompose.framework.database.AppDatabase
import com.vikayarska.kotlinapplicationcompose.framework.database.dao.UserDao
import com.vikayarska.kotlinapplicationcompose.network.api.RandomApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UsersMainModule {

    @Provides
    @Singleton
    fun provideUserDao(dataBase: AppDatabase): UserDao {
        return dataBase.userDao()
    }
}