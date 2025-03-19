package school57kotlin2.demo.client

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import school57kotlin2.demo.configuration.ClientProperties
import java.time.Duration

@Configuration
@ConfigurationProperties("client.http.blacklist")
class BlackListClientProperties: ClientProperties()


@Component
class BlackListClient(
    properties: BlackListClientProperties
) {

    private val client = WebClient.builder()
        .baseUrl(properties.uri)
        .clientConnector(
            ReactorClientHttpConnector(
                HttpClient.create().responseTimeout(Duration.ofSeconds(properties.readTimeoutMs))
            )
        )
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