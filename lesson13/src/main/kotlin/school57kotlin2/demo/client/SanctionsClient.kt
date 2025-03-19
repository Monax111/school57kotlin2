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
@ConfigurationProperties("client.http.sanctions")
class SanctionsProperties : ClientProperties()


@Component
class SanctionsClient(
    properties: SanctionsProperties
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

    fun isSanctions(name: String): Boolean = client
        .get()
        .uri(
            "blacklist",
            mapOf("name" to name)
        )
        .retrieve()
        .bodyToMono(Boolean::class.java)
        .block() ?: throw UserNotFundsException(name, 0)

}
