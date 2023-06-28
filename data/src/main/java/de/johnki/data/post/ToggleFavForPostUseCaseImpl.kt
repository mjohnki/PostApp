package de.johnki.data.post

internal class ToggleFavForPostUseCaseImpl(private val postRepository: PostRepository) : ToggleFavForPostUseCase{
    override suspend fun toggle(postId: Int) {
        val post = postRepository.find(postId)
        postRepository.update(post.copy(fav = !post.fav))
    }
}
