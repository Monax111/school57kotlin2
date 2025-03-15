package school57kotlin2.demo.service

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import school57kotlin2.demo.controller.dto.UserDto

class UserServiceTest {

    lateinit var userService: UserService

    @Test
    fun `когда добавляют пользователя, который отсуствует в списке террористов, он добавляется в базу`() {
        val newUser = UserDto(
            name = "Петя",
            age = 56,
            balance = 35687
        )
        userService.addUser(newUser)

    }

    @Test
    fun `когда добавляют пользователя, который есть в списке террористов, он не добавляется в базу`() {


    }


}