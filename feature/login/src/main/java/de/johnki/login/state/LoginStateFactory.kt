package de.johnki.login.state

import de.johnki.data.login.LoginUseCase
import de.johnki.navigation.AppNavigator
import javax.inject.Inject

interface LoginStateFactory {

    fun content(showError: Boolean): ContentState

    class Impl @Inject constructor(
        private val loginUseCase: LoginUseCase,
        private val appNavigator: AppNavigator
    ) : LoginStateFactory {

        override fun content(showError: Boolean): ContentState =
            ContentState(showError, loginUseCase, appNavigator, this)
    }
}
