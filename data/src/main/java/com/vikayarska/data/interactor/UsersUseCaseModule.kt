package com.vikayarska.data.interactor

import com.vikayarska.domain.usecase.user.AddUsersUseCase
import com.vikayarska.domain.usecase.user.DeleteUserUseCase
import com.vikayarska.domain.usecase.user.GetUserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UsersUseCaseModule {

    @Binds
    @Singleton
    abstract fun bindAddUseCase(
        addUserUseCaseImpl: AddUserUseCaseImpl
    ): AddUsersUseCase


    @Binds
    @Singleton
    abstract fun bindDeleteUseCase(
        deleteUserUseCaseImpl: DeleteUserUseCaseImpl
    ): DeleteUserUseCase

    @Binds
    @Singleton
    abstract fun bindGetUserUseCase(
        getUserUseCase: GetUserUseCaseImpl
    ): GetUserUseCase

}