package de.johnki.postlist

sealed class PostListEvent {
    data class PostClicked(val id: Int): PostListEvent()
    data class PostFavedClicked(val id: Int): PostListEvent()
    object ShowAllPostClicked: PostListEvent()
    object ShowFavedPostClicked: PostListEvent()
}
