package de.johnki.postlist

import de.johnki.data.post.Post
import kotlinx.coroutines.flow.Flow

sealed class PostListUiState {
    object Loading: PostListUiState()
    data class AllPosts(val items: Flow<List<Post>>): PostListUiState()
    data class FavedPosts(val items: Flow<List<Post>>): PostListUiState()
}
