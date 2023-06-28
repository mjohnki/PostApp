package de.johnki.data.post

interface ToggleFavForPostUseCase {
    suspend fun toggle(postId: Int)
}
