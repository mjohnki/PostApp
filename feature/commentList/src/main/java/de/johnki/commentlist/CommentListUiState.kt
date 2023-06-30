package de.johnki.commentlist

import de.johnki.data.comment.PostCommentList
import de.johnki.data.post.Post
import kotlinx.coroutines.flow.Flow

sealed class CommentListUiState {
    object Loading: CommentListUiState()
    data class Content(val items: Flow<PostCommentList>): CommentListUiState()
}
