package school57kotlin2.demo.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import school57kotlin2.demo.entity.UserEntity

data class UserDto(
    @Schema(
        description = "Имя пользователя",
    )
    val name: String,
    @Schema(
        description = "Возраст пользователя",
    )
    val age: Int,
    @Schema(
        description = "Баланс пользователя",
    )
    val balance: Long
)

fun UserDto.toEntity() = UserEntity(
    name = name,
    balance = balance,
    age = age,
)