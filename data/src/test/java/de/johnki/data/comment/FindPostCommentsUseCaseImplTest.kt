package de.johnki.data.comment

import de.johnki.data.post.PostImpl
import de.johnki.data.post.PostRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FindPostCommentsUseCaseImplTest {

    @Test
    fun findCallsCommentAndPost() = runTest {
        // given:
        val postId = 1
        val post = PostImpl(1, 2, "a", "b")
        val comment = CommentImpl(1, 2, "a", "b", "c")
        val postRepository: PostRepository = mockk(relaxUnitFun = true)
        val commentRepository: CommentRepository = mockk(relaxUnitFun = true)
        val useCase = FindPostCommentsUseCaseImpl(postRepository, commentRepository)
        every { postRepository.findPostFlow(postId) } returns flow { post }
        every { commentRepository.findForPost(postId) } returns flow { listOf(comment) }

        // when:
        useCase.find(postId)

        // then:
        coVerify {
            postRepository.findPostFlow(postId)
        }
        coVerify {
            commentRepository.findForPost(postId)
        }
    }
}
