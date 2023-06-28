package de.johnki.data.login

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class UserRepositoryTest {

    private val dao: UserDao = mockk()
    private val repo = UserRepository(dao)

    @Test
    fun insertCallsDao() = runTest {
        // given:
        val user = UserImpl(1)
        coEvery { dao.insert(any()) } just runs

        // when:
        repo.insert(user)

        // then:
        coVerify { dao.insert(user) }
    }

    @Test
    fun findCallsDao() = runTest {
        // given:
        val user = UserImpl(1)
        coEvery { dao.find() } returns user

        // when:
        val result = repo.find()

        // then:
        coVerify { dao.find() }
        assertEquals(user, result)
    }
}
