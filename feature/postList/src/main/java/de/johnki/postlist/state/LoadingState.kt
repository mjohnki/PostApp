package de.johnki.postlist.state

import com.motorro.commonstatemachine.coroutines.CoroutineState
import de.johnki.data.post.FindAllPostsUseCase
import de.johnki.data.post.Post
import de.johnki.data.post.UpdatePostsUseCase
import de.johnki.postlist.PostListUiState
import de.johnki.postlist.PostListEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

class LoadingState(
    private val factory: PostListStateFactory,
    private val updatePostsUseCase: UpdatePostsUseCase
) : CoroutineState<PostListEvent, PostListUiState>() {

    override fun doStart() {
        stateScope.launch {
            updatePostsUseCase.update()
            setMachineState(factory.allPosts())
        }
    }

    override fun doProcess(gesture: PostListEvent) {
        Timber.w("Unsupported event: %s", gesture)
    }
}
