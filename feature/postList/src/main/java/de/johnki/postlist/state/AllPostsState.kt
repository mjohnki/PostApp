package de.johnki.postlist.state

import com.motorro.commonstatemachine.coroutines.CoroutineState
import de.johnki.data.post.FindAllPostsUseCase
import de.johnki.data.post.Post
import de.johnki.data.post.ToggleFavForPostUseCase
import de.johnki.navigation.AppNavigator
import de.johnki.navigation.Destination
import de.johnki.postlist.PostListUiState
import de.johnki.postlist.PostListEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

class AllPostsState(
    private val factory: PostListStateFactory,
    private val findAllPostsUseCase: FindAllPostsUseCase,
    private val toggleFavForPostUseCase: ToggleFavForPostUseCase,
    private val appNavigator: AppNavigator
) : CoroutineState<PostListEvent, PostListUiState>() {

    override fun doStart() {
        stateScope.launch {
            val data: Flow<List<Post>> = findAllPostsUseCase.findAll()
            setUiState(PostListUiState.AllPosts(data))
        }
    }

    override fun doProcess(gesture: PostListEvent) = when (gesture) {
        is PostListEvent.PostClicked -> gotoComments(gesture.id)
        is PostListEvent.ShowFavedPostClicked -> setMachineState(factory.favedPosts())
        is PostListEvent.PostFavedClicked -> toggleFav(gesture.id)
        else -> {
            Timber.w("Unsupported event: %s", gesture)
        }
    }

    private fun gotoComments(postId: Int) {
        stateScope.launch {
            // change to comments
            appNavigator.navigateTo(Destination.CommentListScreen(postId))
        }
    }

    private fun toggleFav(postId: Int) {
        stateScope.launch {
            toggleFavForPostUseCase.toggle(postId)
        }
    }
}
