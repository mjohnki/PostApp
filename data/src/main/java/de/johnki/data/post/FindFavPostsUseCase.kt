package de.johnki.data.post

import kotlinx.coroutines.flow.Flow

interface FindFavPostsUseCase {
    suspend fun findFav()  : Flow<List<Post>>
}
