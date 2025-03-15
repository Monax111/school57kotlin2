package school57kotlin2.demo.service

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import school57kotlin2.demo.client.BlackListClient
import school57kotlin2.demo.controller.dto.UserDto
import school57kotlin2.demo.repository.UserRepository

class UserServiceTest {

    private val blackListClient = mockk<BlackListClient>()
    private val userRepository = mockk<UserRepository>()
    private val userService = UserService(userRepository, blackListClient)

    @Test
    fun `когда добавляют пользователя, который отсуствует в списке террористов, он добавляется в базу`() {
        val newUser = UserDto(
            name = "Петя",
            age = 56,
            balance = 35687
        )

        every { blackListClient.searchInBlacklist(newUser.name, newUser.age) } returns listOf()
        every { userRepository.save(any()) } answers { firstArg() }

        val savedUser = userService.addUser(newUser)

        savedUser shouldBe newUser
    }

    @Test
    fun `когда добавляют пользователя, который есть в списке террористов, он не добавляется в базу`() {


    }


}