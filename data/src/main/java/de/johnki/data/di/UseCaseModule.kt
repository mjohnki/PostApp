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
import de.johnki.data.post.FindAllPostsUseCase
import de.johnki.data.post.FindAllPostsUseCaseImpl
import de.johnki.data.post.FindFavPostsUseCase
import de.johnki.data.post.FindFavPostsUseCaseImpl
import de.johnki.data.post.PostRepository
import de.johnki.data.post.ToggleFavForPostUseCase
import de.johnki.data.post.ToggleFavForPostUseCaseImpl
import de.johnki.data.post.UpdatePostsUseCase
import de.johnki.data.post.UpdatePostsUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
internal class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun providesUserListUseCase(repo: UserRepository): LoginUseCase {
        return LoginUseCaseImpl(repo)
    }

    @Provides
    @ViewModelScoped
    fun providesFindAllPostsUseCase(postRepository: PostRepository): FindAllPostsUseCase {
        return FindAllPostsUseCaseImpl(postRepository)
    }

    @Provides
    @ViewModelScoped
    fun providesFindFavPostsUseCase(postRepository: PostRepository): FindFavPostsUseCase {
        return FindFavPostsUseCaseImpl(postRepository)
    }

    @Provides
    @ViewModelScoped
    fun providesToggleFavForPostUseCaseImpl(postRepository: PostRepository): ToggleFavForPostUseCase {
        return ToggleFavForPostUseCaseImpl(postRepository)
    }

    @Provides
    @ViewModelScoped
    fun providesUpdatePostsUseCase(userRepository: UserRepository, postRepository: PostRepository): UpdatePostsUseCase {
        return UpdatePostsUseCaseImpl(userRepository, postRepository)
    }
}
