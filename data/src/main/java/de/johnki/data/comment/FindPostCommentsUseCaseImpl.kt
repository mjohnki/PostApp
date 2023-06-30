package de.johnki.data.comment

import de.johnki.data.post.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

internal class FindPostCommentsUseCaseImpl(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository
) : FindPostCommentsUseCase {
    override suspend fun find(postId: Int): Flow<PostCommentList> {
        val postFlow = postRepository.findPostFlow(postId)
        val commentsFlow = commentRepository.findForPost(postId)
        return postFlow.combine(commentsFlow) { post, comments ->
            PostCommentListImpl(post, comments)
        }
    }
}
