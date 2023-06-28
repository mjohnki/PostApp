package de.johnki.data.post

import kotlinx.coroutines.flow.Flow

internal class FindAllPostsUseCaseImpl(private val postRepository: PostRepository) : FindAllPostsUseCase{
    override suspend fun findAll() : Flow<List<Post>> = postRepository.findAll()
}
