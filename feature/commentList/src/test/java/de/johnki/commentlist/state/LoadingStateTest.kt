package de.johnki.commentlist.state

import com.motorro.commonstatemachine.CommonStateMachine
import de.johnki.commentlist.CommentListEvent
import de.johnki.commentlist.CommentListUiState
import de.johnki.data.comment.UpdatePostCommentsUseCase
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

    private val stateMachine: CommonStateMachine<CommentListEvent, CommentListUiState> = mockk(relaxed = true)
    private val factory: CommentListStateFactory = mockk()
    private val updatePostCommentsUseCase: UpdatePostCommentsUseCase = mockk(relaxUnitFun = true)

    @Before
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun start_should_display_Content() = runTest {
        // given:
        val postId = 1
        val content : ContentState = mockk()
        val state = LoadingState(factory, postId, updatePostCommentsUseCase)
        every { factory.content(postId) } returns content

        // when:
        state.start(stateMachine)
        advanceUntilIdle()

        // then:
        coVerify {
            updatePostCommentsUseCase.update(postId)
        }
        verify {
            stateMachine.setMachineState(content)
        }
    }
}
