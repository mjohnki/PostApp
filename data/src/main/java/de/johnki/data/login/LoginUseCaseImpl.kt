package de.johnki.data.login

internal class LoginUseCaseImpl(private val repo: UserRepository) : LoginUseCase {

    override suspend fun login(userId: Int) {
        repo.insert(UserImpl(userId))
    }
}
