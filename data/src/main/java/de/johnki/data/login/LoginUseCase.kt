package de.johnki.data.login

interface LoginUseCase {
    suspend fun login(userId: Int)
}
