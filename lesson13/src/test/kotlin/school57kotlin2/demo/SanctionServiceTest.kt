package school57kotlin2.demo

import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.test.context.ActiveProfiles
import school57kotlin2.demo.client.BlackListClient
import school57kotlin2.demo.client.SanctionsClient
import school57kotlin2.demo.controller.UsersController
import school57kotlin2.demo.controller.dto.TransferDto
import school57kotlin2.demo.controller.dto.UserDto
import school57kotlin2.demo.service.SanctionsUserException
import kotlin.random.Random


@SpringBootTest
class SanctionServiceTest : AbstractServiceTest() {

    @Autowired
    lateinit var usersController: UsersController



    @Test
    fun `когда пользователь, находящийся в санкционном листу, переводит деньги, операция не проходит`() {

        val newUser = UserDto(
            name = "Дарт Вейдер",
            age = 556,
            balance = 356874309507420
        )

        every {
            sanctionsClient.isSanctions(newUser.name)
        } returns true

        val transfer = TransferDto(
            to = newUser.name,
            from = "any",
            amount = Random.nextLong()
        )
        shouldThrow<SanctionsUserException> {
            usersController.transferMoney(transfer)
        }

    }
}