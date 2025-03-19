package school57kotlin2.demo.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import school57kotlin2.demo.controller.dto.TransferDto
import school57kotlin2.demo.controller.dto.UserDto
import school57kotlin2.demo.entity.toDto
import school57kotlin2.demo.service.UserService


@RestController
class UsersController(
    val userService: UserService
) {

    @PostMapping("/users")
    fun addUser(
        @RequestBody user: UserDto
    ) {
        userService.addUser(user)
    }

    @GetMapping("/users/{name}")
    fun getUser(
        @PathVariable("name") name: String
    ) : UserDto {
        return userService.getUser(name).toDto()
    }

    @PostMapping("/transfer")
    fun transferMoney(
        @RequestBody transferDto: TransferDto
    ) {
        userService.transferMoney(transferDto)
    }
}