package de.johnki.login.state

import com.motorro.commonstatemachine.CommonStateMachine
import de.johnki.data.login.LoginUseCase
import de.johnki.login.LoginEvent
import de.johnki.login.LoginUiState
import io.mockk.coEvery
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
class ContentStateTest {

    private val stateMachine: CommonStateMachine<LoginEvent, LoginUiState> = mockk(relaxed = true)
    private val factory: LoginStateFactory = mockk()
    private val loginUseCase: LoginUseCase = mockk(relaxUnitFun = true)

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
        val showError = false
        val state = ContentState(showError, loginUseCase, factory)

        // when:
        state.start(stateMachine)

        // then:
        verify {
            stateMachine.setUiState(LoginUiState.Content(showError))
        }
    }

    @Test
    fun doProcess_should_call_login_usecase_and_goto_loginSuccessful_state_when_userId_is_a_number() = runTest {
        // given:
        val userId = "1"
        val gesture = LoginEvent.LoginClicked(userId)
        val state = ContentState(false, loginUseCase, factory)
        state.start(stateMachine)
        val loginSuccessful = LoginSuccessfulState()
        coEvery { factory.loginSuccessful() } returns loginSuccessful

        // when:
        state.process(gesture)
        advanceUntilIdle()

        // then:
        coVerify { loginUseCase.login(any()) }
        verify { stateMachine.setMachineState(loginSuccessful) }
    }

    @Test
    fun doProcess_should_goto_content_error_state_when_userId_is_empty() = runTest {
        // given:
        val userId = ""
        val gesture = LoginEvent.LoginClicked(userId)
        val state = ContentState(false, loginUseCase, factory)
        state.start(stateMachine)
        val error = ContentState(true, loginUseCase, factory)
        every { factory.content(true) } returns error

        // when:
        state.process(gesture)
        advanceUntilIdle()

        // then:
        verify { stateMachine.setMachineState(error) }
        coVerify(exactly = 0) { loginUseCase.login(any()) }
    }

    @Test
    fun doProcess_should_goto_content_error_state_when_userId_is_not_a_number() = runTest {
        // given:
        val userId = "m"
        val gesture = LoginEvent.LoginClicked(userId)
        val state = ContentState(false, loginUseCase, factory)
        state.start(stateMachine)
        val error = ContentState(true, loginUseCase, factory)
        every { factory.content(true) } returns error

        // when:
        state.process(gesture)
        advanceUntilIdle()

        // then:
        verify { stateMachine.setMachineState(error) }
        coVerify(exactly = 0) { loginUseCase.login(any()) }
    }

}
