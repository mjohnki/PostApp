package de.johnki.postlist.state

import de.johnki.data.post.FindAllPostsUseCase
import de.johnki.data.post.FindFavPostsUseCase
import de.johnki.data.post.ToggleFavForPostUseCase
import de.johnki.data.post.UpdatePostsUseCase
import de.johnki.navigation.AppNavigator
import javax.inject.Inject

interface PostListStateFactory {

    fun loading(): LoadingState

    fun allPosts(): AllPostsState

    fun favedPosts(): FavedPostsState

    class Impl @Inject constructor(
        private val findAllPostsUseCase: FindAllPostsUseCase,
        private val updatePostsUseCase: UpdatePostsUseCase,
        private val findFavPostsUseCase: FindFavPostsUseCase,
        private val toggleFavForPostUseCase: ToggleFavForPostUseCase,
        private val appNavigator: AppNavigator
    ) : PostListStateFactory {

        override fun loading() = LoadingState(this, updatePostsUseCase)

        override fun allPosts() =  AllPostsState(this, findAllPostsUseCase, toggleFavForPostUseCase, appNavigator)

        override fun favedPosts() = FavedPostsState(this, findFavPostsUseCase, toggleFavForPostUseCase, appNavigator)
    }
}
