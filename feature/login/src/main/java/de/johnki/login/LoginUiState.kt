package de.johnki.login

sealed class LoginUiState {
    data class Content(val showError: Boolean): LoginUiState()
}
