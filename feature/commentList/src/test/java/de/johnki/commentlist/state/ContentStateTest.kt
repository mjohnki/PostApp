package de.johnki.commentlist.state

import com.motorro.commonstatemachine.CommonStateMachine
import de.johnki.commentlist.CommentListEvent
import de.johnki.commentlist.CommentListUiState
import de.johnki.data.comment.FindPostCommentsUseCase
import de.johnki.data.comment.PostCommentList
import de.johnki.data.post.ToggleFavForPostUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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
class ContentStateTest {

    private val stateMachine: CommonStateMachine<CommentListEvent, CommentListUiState> = mockk(relaxed = true)
    private val findPostCommentsUseCase: FindPostCommentsUseCase = mockk()
    private val toggleFavForPostUseCase: ToggleFavForPostUseCase = mockk(relaxUnitFun = true)

    @Before
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())
        val postCommentList : PostCommentList = mockk()
        val data : Flow<PostCommentList> = flow { postCommentList  }
        coEvery { findPostCommentsUseCase.find(any()) } returns data
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun start_should_load_data() = runTest {
        // given:
        val postId = 1
        val postCommentList : PostCommentList = mockk()
        val data : Flow<PostCommentList> = flow { postCommentList  }
        val state = ContentState(postId, findPostCommentsUseCase, toggleFavForPostUseCase)
        coEvery { findPostCommentsUseCase.find(postId) } returns data

        // when:
        state.start(stateMachine)
        advanceUntilIdle()

        // then:
        coVerify { findPostCommentsUseCase.find(postId) }
        verify { stateMachine.setUiState(CommentListUiState.Content(data)) }
    }

    @Test
    fun doProcess_should_toggle_fav_when_fav_clicked() = runTest {
        // given:
        val postId = 1
        val event = CommentListEvent.PostFavedClicked(postId)
        val state = ContentState(postId, findPostCommentsUseCase, toggleFavForPostUseCase)
        state.start(stateMachine)

        // when:
        state.process(event)
        advanceUntilIdle()

        // then:
        coVerify { toggleFavForPostUseCase.toggle(postId) }
    }

}
