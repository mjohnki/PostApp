package de.johnki.data.login

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class LoginUseCaseImplTest {
    @Test
    fun invokeCallsInsert() = runTest {
        // given:
        val userId = 1
        val repo: UserRepository = mockk()
        val useCase = LoginUseCaseImpl(repo)
        coEvery { repo.insert(any()) } just runs

        // when:
        useCase.login(userId)

        // then:
        coVerify {
            repo.insert(withArg {
                assertTrue(it.id == userId)
            })
        }
    }
}
