package de.johnki.data.post

import kotlinx.coroutines.flow.Flow

internal class PostRepository(
    private val dao: PostDao,
    private val endpoint: PostEndpoint
) {
    suspend fun updateData(userId: Int){
        val posts = endpoint.getPosts(userId)
        dao.insert(posts)
    }

    suspend fun find(postId: Int) = dao.find(postId)

    fun findAll(): Flow<List<PostImpl>> = dao.findAll()

    fun findFav(): Flow<List<PostImpl>> = dao.findFav()

    suspend fun update(postId: PostImpl) = dao.update(postId)
}
