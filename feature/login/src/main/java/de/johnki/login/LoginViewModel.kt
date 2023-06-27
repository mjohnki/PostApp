package de.johnki.login

import androidx.lifecycle.ViewModel
import com.motorro.commonstatemachine.CommonMachineState
import com.motorro.commonstatemachine.coroutines.FlowStateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import de.johnki.login.state.LoginStateFactory
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val factory: LoginStateFactory.Impl,
) : ViewModel() {

    private fun initStateMachine(): CommonMachineState<LoginEvent, LoginUiState> = factory.content(false)
    private val stateMachine = FlowStateMachine(LoginUiState.Content(false), ::initStateMachine)

    val state: StateFlow<LoginUiState> = stateMachine.uiState

    fun process(gesture: LoginEvent) {
        stateMachine.process(gesture)
    }
}
