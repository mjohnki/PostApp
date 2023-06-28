package de.johnki.postlist.state

import com.motorro.commonstatemachine.CommonStateMachine
import de.johnki.data.post.UpdatePostsUseCase
import de.johnki.postlist.PostListEvent
import de.johnki.postlist.PostListUiState
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoadingStateTest {

    private val stateMachine: CommonStateMachine<PostListEvent, PostListUiState> = mockk(relaxed = true)
    private val factory: PostListStateFactory = mockk()
    private val updatePostsUseCase: UpdatePostsUseCase  = mockk(relaxUnitFun = true)

    @Before
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun start_should_update_data_and_display_goto_allPosts_state() = runTest {
        // given:
        val allPostsState : AllPostsState = mockk()
        val state = LoadingState(factory, updatePostsUseCase)
        every { factory.allPosts() } returns allPostsState

        // when:
        state.start(stateMachine)
        advanceUntilIdle()

        // then:
        coVerify { updatePostsUseCase.update() }
        verify { stateMachine.setMachineState(allPostsState) }
    }
}
