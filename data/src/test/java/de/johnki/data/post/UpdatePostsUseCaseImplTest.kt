package de.johnki.data.post

import de.johnki.data.login.UserImpl
import de.johnki.data.login.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UpdatePostsUseCaseImplTest {

    private val userRepository: UserRepository = mockk()
    private val postRepository: PostRepository = mockk(relaxUnitFun = true)
    private val usecase = UpdatePostsUseCaseImpl(userRepository, postRepository)

    @Test
    fun updateCallsUpdate() = runTest {
        // given:
        val user = UserImpl(1)
        coEvery { userRepository.find() } returns user

        // when:
        usecase.update()

        // then:
        coVerify { userRepository.find() }
        coVerify { postRepository.updateData(user.id) }
    }
}
