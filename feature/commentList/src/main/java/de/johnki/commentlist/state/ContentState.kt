package de.johnki.commentlist.state

import com.motorro.commonstatemachine.coroutines.CoroutineState
import de.johnki.commentlist.CommentListEvent
import de.johnki.commentlist.CommentListUiState
import de.johnki.data.comment.FindPostCommentsUseCase
import de.johnki.data.comment.PostCommentList
import de.johnki.data.post.ToggleFavForPostUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ContentState(
    private val postId: Int,
    private val findPostCommentsUseCase: FindPostCommentsUseCase,
    private val toggleFavForPostUseCase: ToggleFavForPostUseCase
) : CoroutineState<CommentListEvent, CommentListUiState>() {

    override fun doStart() {
        stateScope.launch {
            val data: Flow<PostCommentList> = findPostCommentsUseCase.find(postId)
            setUiState(CommentListUiState.Content(data))
        }
    }

    override fun doProcess(gesture: CommentListEvent) = when (gesture) {
        is CommentListEvent.PostFavedClicked -> toggleFav(gesture.id)
    }

    private fun toggleFav(postId: Int) {
        stateScope.launch {
            toggleFavForPostUseCase.toggle(postId)
        }
    }
}
