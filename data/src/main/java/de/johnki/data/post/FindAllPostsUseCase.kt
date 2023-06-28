package de.johnki.data.post

import kotlinx.coroutines.flow.Flow

interface FindAllPostsUseCase {
    suspend fun findAll()  : Flow<List<Post>>
}
