package de.johnki.postlist.state

import com.motorro.commonstatemachine.CommonMachineState
import com.motorro.commonstatemachine.coroutines.CoroutineState
import de.johnki.data.post.FindAllPostsUseCase
import de.johnki.data.post.Post
import de.johnki.postlist.PostListUiState
import de.johnki.postlist.PostListEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

class GoToCommentsState(private val postId: Int): CommonMachineState<PostListEvent, PostListUiState>() {

    override fun doStart() {
        setUiState(PostListUiState.GotoCommentsScreen(postId))
    }

    override fun doProcess(gesture: PostListEvent) {
        Timber.w("Unsupported event: %s", gesture)
    }
}
