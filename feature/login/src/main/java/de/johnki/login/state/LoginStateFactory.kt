package de.johnki.login.state

import de.johnki.data.login.LoginUseCase
import javax.inject.Inject

interface LoginStateFactory {

    fun content(showError: Boolean): ContentState

    fun loginSuccessful(): LoginSuccessfulState

    class Impl @Inject constructor(
        private val loginUseCase: LoginUseCase
    ) : LoginStateFactory {

        override fun content(showError: Boolean): ContentState = ContentState(showError, loginUseCase, this)

        override fun loginSuccessful(): LoginSuccessfulState = LoginSuccessfulState()
    }
}
