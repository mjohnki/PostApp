package de.johnki.data.post

import kotlinx.coroutines.flow.Flow

internal class FindFavPostsUseCaseImpl(private val postRepository: PostRepository) : FindFavPostsUseCase{
    override suspend fun findFav() : Flow<List<Post>> = postRepository.findFav()
}
