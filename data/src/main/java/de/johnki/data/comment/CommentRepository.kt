package de.johnki.data.comment

import kotlinx.coroutines.flow.Flow

internal class CommentRepository(
    private val dao: CommentDao,
    private val endpoint: CommentEndpoint
) {
    suspend fun updateData(postId: Int){
        val comments = endpoint.getCommentsForPostId(postId)
        dao.insert(comments)
    }

    fun findForPost(postId: Int): Flow<List<CommentImpl>> = dao.findByPostId(postId)
}
