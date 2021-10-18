package com.vikayarska.data.interactor

import com.vikayarska.domain.usecase.user.AddUsersUseCase
import com.vikayarska.domain.usecase.user.DeleteUserUseCase
import com.vikayarska.domain.usecase.user.UserUseCaseFacade
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UsersUseCaseFacadeModule {

    @Provides
    @Singleton
    fun provideUserUseCaseFacade(
        addUser: AddUsersUseCase, deleteUser: DeleteUserUseCase
    ): UserUseCaseFacade {
        return UserUseCaseFacade(addUser, deleteUser)
    }
}