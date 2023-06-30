package de.johnki.commentlist.state

import de.johnki.data.comment.FindPostCommentsUseCase
import de.johnki.data.comment.UpdatePostCommentsUseCase
import de.johnki.data.post.ToggleFavForPostUseCase
import javax.inject.Inject

interface CommentListStateFactory {

    fun loading(postId: Int): LoadingState

    fun content(postId: Int): ContentState

    class Impl @Inject constructor(
        private val updateCommentsUseCase: UpdatePostCommentsUseCase,
        private val findPostCommentsUseCase: FindPostCommentsUseCase,
        private val toggleFavForPostUseCase: ToggleFavForPostUseCase
    ) : CommentListStateFactory {

        override fun loading(postId: Int) = LoadingState(this, postId, updateCommentsUseCase)

        override fun content(postId: Int) = ContentState(postId, findPostCommentsUseCase, toggleFavForPostUseCase)
    }
}
