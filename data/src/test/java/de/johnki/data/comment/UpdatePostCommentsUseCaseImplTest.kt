package de.johnki.data.comment

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UpdatePostCommentsUseCaseImplTest {

    @Test
    fun updateCallsUpdateData() = runTest {
        // given:
        val postId = 1
        val repo: CommentRepository = mockk(relaxUnitFun = true)
        val useCase = UpdatePostCommentsUseCaseImpl(repo)

        // when:
        useCase.update(postId)

        // then:
        coVerify {
            repo.updateData(postId)
        }
    }
}
