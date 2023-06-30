package de.johnki.data.comment

import kotlinx.coroutines.flow.Flow

interface FindPostCommentsUseCase {
    suspend fun find(postId: Int): Flow<PostCommentList>
}
