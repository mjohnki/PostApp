package de.johnki.data.post

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class PostRepositoryTest {

    private val dao: PostDao = mockk(relaxUnitFun = true)
    private val endpoint: PostEndpoint = mockk()
    private val repo = PostRepository(dao, endpoint)

    @Test
    fun findAllCallsDao() = runTest {
        // given:
        val posts : Flow<List<PostImpl>> = flow {  emptyList<PostImpl>() }
        coEvery { dao.findAll() } returns posts

        // when:
        val result = repo.findAll()

        // then:
        coVerify { dao.findAll() }
        assertEquals(posts, result)
    }

    @Test
    fun findFavCallsDao() = runTest {
        // given:
        val posts : Flow<List<PostImpl>> = flow {  emptyList<PostImpl>() }
        coEvery { dao.findFav() } returns posts

        // when:
        val result = repo.findFav()

        // then:
        coVerify { dao.findFav() }
        assertEquals(posts, result)
    }

    @Test
    fun findCallsDao() = runTest {
        // given:
        val postId = 1
        val post = PostImpl(id = postId, userId = 1, title = "", body = "", fav = false)
        coEvery { dao.find(postId) } returns post

        // when:
        val result = repo.find(postId)

        // then:
        coVerify { dao.find(postId) }
        assertEquals(post, result)
    }

    @Test
    fun updateCallsDao() = runTest {
        // given:
        val post = PostImpl(id = 1, userId = 1, title = "", body = "", fav = false)

        // when:
        repo.update(post)

        // then:
        coVerify { dao.update(post) }
    }

    @Test
    fun updateCallsEndpointAndSaveResult() = runTest {
        // given:
        val userId = 1
        val posts : List<PostImpl> = emptyList()
        coEvery { endpoint.getPosts(any()) } returns posts

        // when:
        repo.updateData(userId)

        // then:
        coVerify { endpoint.getPosts(userId) }
        coVerify { dao.insert(posts) }
    }
}
