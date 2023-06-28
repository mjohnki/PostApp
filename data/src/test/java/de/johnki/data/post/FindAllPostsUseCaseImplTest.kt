package de.johnki.data.post

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FindAllPostsUseCaseImplTest {

    private val postRepository: PostRepository = mockk(relaxUnitFun = true)
    private val usecase = FindAllPostsUseCaseImpl(postRepository)

    @Test
    fun findCallsFind() = runTest {
        // given:
        val posts = flow<List<PostImpl>> { emptyList<PostImpl>() }
        coEvery { usecase.findAll() } returns posts

        // when:
        val result = usecase.findAll()

        // then:
        coVerify { postRepository.findAll() }
        assertEquals(posts, result)
    }
}
