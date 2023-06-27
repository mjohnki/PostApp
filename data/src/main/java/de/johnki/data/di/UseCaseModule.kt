package de.johnki.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import de.johnki.data.login.LoginUseCase
import de.johnki.data.login.LoginUseCaseImpl
import de.johnki.data.login.UserRepository

@Module
@InstallIn(ViewModelComponent::class)
internal class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun providesUserListUseCase(repo: UserRepository): LoginUseCaseImpl {
        return LoginUseCaseImpl(repo)
    }

    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class UseCaseBindModule {

        @Binds
        abstract fun bindLoginUseCase(
            loginUseCase: LoginUseCaseImpl
        ): LoginUseCase
    }
}
