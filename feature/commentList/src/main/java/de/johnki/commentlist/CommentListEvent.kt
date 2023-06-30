package de.johnki.commentlist

sealed class CommentListEvent {
    data class PostFavedClicked(val id: Int): CommentListEvent()
}
