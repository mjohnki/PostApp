package de.johnki.data.comment

internal class UpdatePostCommentsUseCaseImpl(private val commentRepository: CommentRepository) :
    UpdatePostCommentsUseCase {
    override suspend fun update(postId: Int) {
        commentRepository.updateData(postId)
    }
}
