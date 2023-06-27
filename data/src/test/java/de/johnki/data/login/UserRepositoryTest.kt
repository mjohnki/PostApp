package de.johnki.data.login

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UserRepositoryTest {
    @Test
    fun insertCallsDao() = runTest {
        // given:
        val user = UserImpl(1)
        val dao: UserDao = mockk()
        val repo = UserRepository(dao)
        coEvery { dao.insert(any()) } just runs

        // when:
        repo.insert(user)

        // then:
        coVerify { dao.insert(user) }
    }
}
