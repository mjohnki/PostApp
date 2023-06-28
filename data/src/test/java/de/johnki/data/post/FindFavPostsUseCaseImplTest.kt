package de.johnki.data.post

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FindFavPostsUseCaseImplTest {

    private val postRepository: PostRepository = mockk(relaxUnitFun = true)
    private val usecase = FindFavPostsUseCaseImpl(postRepository)

    @Test
    fun findCallsFind() = runTest {
        // given:
        val posts = flow<List<PostImpl>> { emptyList<PostImpl>() }
        coEvery { usecase.findFav() } returns posts

        // when:
        val result = usecase.findFav()

        // then:
        coVerify { postRepository.findFav() }
        assertEquals(posts, result)
    }
}
