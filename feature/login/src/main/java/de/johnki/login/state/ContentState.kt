package de.johnki.login.state

import com.motorro.commonstatemachine.coroutines.CoroutineState
import de.johnki.data.login.LoginUseCase
import de.johnki.login.LoginEvent
import de.johnki.login.LoginUiState
import de.johnki.navigation.AppNavigator
import de.johnki.navigation.Destination
import kotlinx.coroutines.launch

class ContentState(
    private val showError: Boolean,
    private val loginUseCase: LoginUseCase,
    private val appNavigator: AppNavigator,
    private val factory: LoginStateFactory
) : CoroutineState<LoginEvent, LoginUiState>() {

    override fun doStart() {
        setUiState(LoginUiState.Content(showError))
    }

    override fun doProcess(gesture: LoginEvent) = when (gesture) {
        is LoginEvent.LoginClicked -> onLoginClicked(gesture.userId)
    }

    private fun onLoginClicked(userId: String) {
        stateScope.launch {
            val id: Int? = userId.toIntOrNull()
            if (id != null) {
                loginUseCase.login(id)
                appNavigator.navigateTo(Destination.PostListScreen())
            } else {
                setMachineState(factory.content(true))
            }
        }
    }
}
