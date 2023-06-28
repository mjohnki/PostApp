package de.johnki.data.post

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class ToggleFavForPostUseCaseImplTest {

    private val postRepository: PostRepository = mockk(relaxUnitFun = true)
    private val usecase = ToggleFavForPostUseCaseImpl(postRepository)

    @Test
    fun toggleCallsUpdate() = runTest {
        // given:
        val postId = 1
        val post = PostImpl(id = postId, userId = 1, title = "", body = "", fav = false)
        coEvery { postRepository.find(postId) } returns post

        // when:
        usecase.toggle(postId)

        // then:
        coVerify { postRepository.find(postId) }
        coVerify { postRepository.update(withArg {
            assertTrue(it.fav)
        }) }
    }
}
