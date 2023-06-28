package de.johnki.data.post

import de.johnki.data.login.UserRepository

internal class UpdatePostsUseCaseImpl(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) : UpdatePostsUseCase {
    override suspend fun update() {
        val user = userRepository.find()
        postRepository.updateData(user.id)
    }
}
