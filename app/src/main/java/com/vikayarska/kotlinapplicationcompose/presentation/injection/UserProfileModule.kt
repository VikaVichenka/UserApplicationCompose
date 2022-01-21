package com.vikayarska.kotlinapplicationcompose.presentation.injection

import com.vikayarska.data.mapper.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UserProfileModule {
    @Singleton
    @Provides
    fun provideMapFunctionAppUser(): UserToAppUserMapper {
        return UserToAppUserMapper(::mapUserToApplicationUser)
    }

    @Singleton
    @Provides
    fun provideMapFunctionDbUser(): DbToUserMapper {
        return DbToUserMapper(::mapDBUser)
    }
}