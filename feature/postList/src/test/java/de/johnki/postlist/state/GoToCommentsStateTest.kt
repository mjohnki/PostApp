package de.johnki.postlist.state

import com.motorro.commonstatemachine.CommonStateMachine
import de.johnki.postlist.PostListEvent
import de.johnki.postlist.PostListUiState
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GoToCommentsStateTest {

    private val stateMachine: CommonStateMachine<PostListEvent, PostListUiState> = mockk(relaxed = true)

    @Test
    fun start_should_goto_ui_state_GoToCommentsState() = runTest {
        // given:
        val postId = 1
        val state = GoToCommentsState(postId)

        // when:
        state.start(stateMachine)

        // then:
        verify { stateMachine.setUiState(PostListUiState.GotoCommentsScreen(postId)) }
    }
}
