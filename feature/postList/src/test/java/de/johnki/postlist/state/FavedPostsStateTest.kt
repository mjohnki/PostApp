package de.johnki.postlist.state

import com.motorro.commonstatemachine.CommonStateMachine
import de.johnki.data.post.FindFavPostsUseCase
import de.johnki.data.post.Post
import de.johnki.data.post.ToggleFavForPostUseCase
import de.johnki.navigation.AppNavigator
import de.johnki.navigation.Destination
import de.johnki.postlist.PostListEvent
import de.johnki.postlist.PostListUiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavedPostsStateTest {

    private val stateMachine: CommonStateMachine<PostListEvent, PostListUiState> = mockk(relaxed = true)
    private val factory: PostListStateFactory = mockk()
    private val findFavPostsUseCase: FindFavPostsUseCase = mockk(relaxUnitFun = true)
    private val toggleFavForPostUseCase: ToggleFavForPostUseCase = mockk(relaxUnitFun = true)
    private val appNavigator: AppNavigator = mockk(relaxUnitFun = true)
    private val posts = flow<List<Post>> { emptyList<Post>() }

    @Before
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())
        coEvery { findFavPostsUseCase.findFav() } returns posts
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun start_should_display_FavPosts() = runTest {
        // given:
        val state = FavedPostsState(factory, findFavPostsUseCase, toggleFavForPostUseCase, appNavigator)

        // when:
        state.start(stateMachine)
        advanceUntilIdle()

        // then:
        coVerify { findFavPostsUseCase.findFav() }
        verify { stateMachine.setUiState(PostListUiState.FavedPosts(posts)) }
    }

    @Test
    fun doProcess_should_goto_gotoComments_state_when_post_is_clicked() = runTest {
        // given:
        val postId = 1
        val event = PostListEvent.PostClicked(postId)
        val state = FavedPostsState(factory, findFavPostsUseCase, toggleFavForPostUseCase, appNavigator)
        state.start(stateMachine)

        // when:
        state.process(event)
        advanceUntilIdle()

        // when:
        coVerify { appNavigator.navigateTo(Destination.CommentListScreen(postId)) }
    }

    @Test
    fun doProcess_should_goto_favedPosts_state_when_ShowFavedPosts_is_clicked() = runTest {
        // given:
        val allPosts: AllPostsState = mockk()
        val event = PostListEvent.ShowAllPostClicked
        val state = FavedPostsState(factory, findFavPostsUseCase, toggleFavForPostUseCase, appNavigator)
        state.start(stateMachine)
        every { factory.allPosts() } returns allPosts

        // when:
        state.process(event)

        // when:
        verify { stateMachine.setMachineState(allPosts) }
    }

    @Test
    fun doProcess_should_goto_toggle_fav_post_when_toggleFav_is_clicked() = runTest {
        // given:
        val postId = 1
        val event = PostListEvent.PostFavedClicked(postId)
        val state = FavedPostsState(factory, findFavPostsUseCase, toggleFavForPostUseCase, appNavigator)
        state.start(stateMachine)

        // when:
        state.process(event)
        advanceUntilIdle()

        // when:
        coVerify { toggleFavForPostUseCase.toggle(postId) }
    }
}
