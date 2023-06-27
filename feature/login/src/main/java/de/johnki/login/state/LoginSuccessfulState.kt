package de.johnki.login.state

import com.motorro.commonstatemachine.coroutines.CoroutineState
import de.johnki.data.login.LoginUseCase
import de.johnki.login.LoginEvent
import de.johnki.login.LoginUiState

class LoginSuccessfulState : CoroutineState<LoginEvent, LoginUiState>() {

    override fun doStart() {
        setUiState(LoginUiState.LoginSuccessful)
    }
}
