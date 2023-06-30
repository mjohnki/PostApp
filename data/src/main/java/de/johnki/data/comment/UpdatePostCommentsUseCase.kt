package de.johnki.data.comment

interface UpdatePostCommentsUseCase {
    suspend fun update(postId: Int)
}
