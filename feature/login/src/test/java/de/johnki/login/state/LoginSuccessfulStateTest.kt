package de.johnki.login.state

import com.motorro.commonstatemachine.CommonStateMachine
import com.motorro.commonstatemachine.coroutines.CoroutineState
import de.johnki.data.login.LoginUseCase
import de.johnki.login.LoginEvent
import de.johnki.login.LoginUiState
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginSuccessfulStateTest {

    private val stateMachine: CommonStateMachine<LoginEvent, LoginUiState> = mockk(relaxed = true)

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
        val state = LoginSuccessfulState()

        // when:
        state.start(stateMachine)

        // then:
        verify {
            stateMachine.setUiState(LoginUiState.LoginSuccessful)
        }
    }
}
