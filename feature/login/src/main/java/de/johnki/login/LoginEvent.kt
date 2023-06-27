package de.johnki.login

sealed class LoginEvent {
    data class LoginClicked(val userId: String): LoginEvent()
}
