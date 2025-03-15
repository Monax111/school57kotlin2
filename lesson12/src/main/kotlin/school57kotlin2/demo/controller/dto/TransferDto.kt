package school57kotlin2.demo.controller.dto

import io.swagger.v3.oas.annotations.media.Schema

class TransferDto(
    @Schema(
        description = "Имя пользователя, который переводит деньги",
    )
    val from: String,
    @Schema(
        description = "Имя пользователя, которому переводят деньги",
    )
    val to: String,
    @Schema(
        description = "Сумма перевода",
    )
    val amount: Long
)