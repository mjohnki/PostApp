package de.johnki.commentlist.state

import com.motorro.commonstatemachine.coroutines.CoroutineState
import de.johnki.commentlist.CommentListUiState
import de.johnki.commentlist.CommentListEvent
import de.johnki.data.comment.UpdatePostCommentsUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class LoadingState(
    private val factory: CommentListStateFactory,
    private val postId: Int,
    private val updatePostCommentsUseCase: UpdatePostCommentsUseCase
) : CoroutineState<CommentListEvent, CommentListUiState>() {

    override fun doStart() {
        stateScope.launch {
            updatePostCommentsUseCase.update(postId)
            setMachineState(factory.content(postId))
        }
    }

    override fun doProcess(gesture: CommentListEvent) {
        Timber.w("Unsupported event: %s", gesture)
    }
}
