package school57kotlin2.demo.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.ResponseStatus
import school57kotlin2.demo.client.BlackListClient
import school57kotlin2.demo.client.SanctionsClient
import school57kotlin2.demo.controller.dto.TransferDto
import school57kotlin2.demo.controller.dto.UserDto
import school57kotlin2.demo.controller.dto.toEntity
import school57kotlin2.demo.repository.UserRepository

@Service
class UserService(
    val userRepository: UserRepository,
    val blackListClient: BlackListClient,
    val sanctionsClient: SanctionsClient,
) {

    fun addUser(user: UserDto) {
        val blackListReason = blackListClient.searchInBlacklist(
            name = user.name,
            age = user.age
        )

        if (blackListReason.contains("Террорист")) {
            return
        }

        userRepository.save(user.toEntity())
    }

    fun getUser(name: String) =
        userRepository.findByName(name) ?: throw UserNotFoundException(name)

     @Transactional
    fun transferMoney(transferDto: TransferDto) {

        if (sanctionsClient.isSanctions(transferDto.to)) {
            throw SanctionsUserException()
        }

        val fromUser = getUser(transferDto.from)
        val toUser = getUser(transferDto.to)

        toUser.balance += transferDto.amount
        userRepository.save(toUser)

        if (fromUser.balance < transferDto.amount) {
            throw InsufficientFundsException(fromUser.name)
        }
        fromUser.balance -= transferDto.amount
        userRepository.save(fromUser)
    }

}

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InsufficientFundsException(name: String) :
    RuntimeException("Недостаточно средств для перевода y пользователя $name")

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException(name: String) : RuntimeException("User $name not found in DB")

@ResponseStatus(HttpStatus.BAD_REQUEST)
class SanctionsUserException   : RuntimeException("User has sanctions")