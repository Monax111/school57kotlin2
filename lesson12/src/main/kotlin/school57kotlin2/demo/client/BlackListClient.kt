package school57kotlin2.demo.client

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.reactive.function.client.WebClient

@Component
class BlackListClient {

    private val client = WebClient.builder()
        .baseUrl("http://localhost:8090/")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    fun searchInBlacklist(name: String, age: Int): List<String> = client
        .get()
        .uri(
            "blacklist",
            mapOf("name" to name, "age" to age)
        )
        .retrieve()
        .bodyToMono(String::class.java)
        .block()
        ?.split(",") ?: throw UserNotFundsException(name, age)

}

@ResponseStatus(HttpStatus.BAD_REQUEST)
class UserNotFundsException(name: String, age: Int) :
    RuntimeException("Не нашли пользователя $name, $age")