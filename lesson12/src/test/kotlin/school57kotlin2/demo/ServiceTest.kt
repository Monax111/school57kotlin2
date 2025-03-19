//package school57kotlin2.demo
//
//import com.ninjasquad.springmockk.MockkBean
//import io.kotest.matchers.shouldNotBe
//import io.mockk.every
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import school57kotlin2.demo.client.BlackListClient
//import school57kotlin2.demo.controller.dto.UserDto
//import school57kotlin2.demo.repository.UserRepository
//import school57kotlin2.demo.service.UserService
//
//@SpringBootTest
//class ServiceTest {
//
//
//    @MockkBean
//    lateinit var blackListClient: BlackListClient
//
//    @Autowired
//    lateinit var userService: UserService
//
//    @Autowired
//    lateinit var userRepository: UserRepository
//
//    @Test
//    fun `когда добавляют пользователя, который отсуствует в списке террористов, он добавляется в базу`() {
//        val newUser = UserDto(
//            name = "Петя",
//            age = 56,
//            balance = 35687
//        )
//
//        every { blackListClient.searchInBlacklist(newUser.name, newUser.age) } returns listOf()
//
//        userService.addUser(newUser)
//
//        val user = userRepository.findByName(newUser.name)
//
//        user shouldNotBe null
//    }
//
//}