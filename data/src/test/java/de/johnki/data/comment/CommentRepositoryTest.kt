package de.johnki.data.comment

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CommentRepositoryTest {

    @Test
    fun findForPostCallsDao() = runTest {
        // given:
        val postId = 1
        val comment = CommentImpl(1, 2, "a", "b", "c")
        val dao: CommentDao = mockk(relaxUnitFun = true)
        val endpoint: CommentEndpoint = mockk(relaxUnitFun = true)
        val commentRepository = CommentRepository(dao, endpoint)
        every { dao.findByPostId(postId) } returns flow { listOf(comment) }

        // when:
        commentRepository.findForPost(postId)

        // then:
        coVerify {
            dao.findByPostId(postId)
        }
    }

    @Test
    fun updateDataLoadsDataAndSave() = runTest {
        // given:
        val postId = 1
        val comment = CommentImpl(1, 2, "a", "b", "c")
        val dao: CommentDao = mockk(relaxUnitFun = true)
        val endpoint: CommentEndpoint = mockk(relaxUnitFun = true)
        val commentRepository = CommentRepository(dao, endpoint)
        coEvery { endpoint.getCommentsForPostId(postId) } returns listOf(comment)

        // when:
        commentRepository.updateData(postId)

        // then:
        coVerify {
            endpoint.getCommentsForPostId(postId)
        }
        coVerify { dao.insert(any()) }
    }

}
