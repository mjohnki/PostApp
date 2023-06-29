package de.johnki.postlist.state

import com.motorro.commonstatemachine.coroutines.CoroutineState
import de.johnki.data.post.FindFavPostsUseCase
import de.johnki.data.post.Post
import de.johnki.data.post.ToggleFavForPostUseCase
import de.johnki.navigation.AppNavigator
import de.johnki.navigation.Destination
import de.johnki.postlist.PostListEvent
import de.johnki.postlist.PostListUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

class FavedPostsState(
    private val factory: PostListStateFactory,
    private val findFavPostsUseCase: FindFavPostsUseCase,
    private val toggleFavForPostUseCase: ToggleFavForPostUseCase,
    private val appNavigator: AppNavigator
) : CoroutineState<PostListEvent, PostListUiState>() {

    override fun doStart() {
        stateScope.launch {
            val data: Flow<List<Post>> = findFavPostsUseCase.findFav()
            setUiState(PostListUiState.FavedPosts(data))
        }
    }

    override fun doProcess(gesture: PostListEvent) = when (gesture) {
        is PostListEvent.PostClicked -> gotoComments()
        is PostListEvent.ShowAllPostClicked -> setMachineState(factory.allPosts())
        is PostListEvent.PostFavedClicked -> toggleFav(gesture.id)
        else -> {
            Timber.w("Unsupported event: %s", gesture)
        }
    }

    private fun gotoComments() {
        stateScope.launch {
            // change to comments
            appNavigator.navigateTo(Destination.PostListScreen())
        }
    }

    private fun toggleFav(postId: Int) {
        stateScope.launch {
            toggleFavForPostUseCase.toggle(postId)
        }
    }
}
