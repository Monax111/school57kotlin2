package school57kotlin2.demo.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import school57kotlin2.demo.controller.dto.UserDto

@Entity(name = "users")
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val name: String,
    var balance: Long,
)

fun UserEntity.toDto() = UserDto(name, balance)