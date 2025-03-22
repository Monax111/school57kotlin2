package school57kotlin2.demo.service

import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@EnableScheduling
@Service
class ClockService(
    // maybe use @Value for timeout and url ? https://www.baeldung.com/spring-value-annotation
    // @Value("${'$'}{my.config.timeout}")
    // val timeout: Long
    // same for url
) {

    // just counter
    var count = 1

    // Execute every 1000 ms
    @Scheduled(fixedRate = 1000)
    fun ping() {
        val timeout = 30
        val url = "https://www.google.com"
        println("Выполняем запрос ${count++} к ресурсу $url с таймаутом $timeout секунд")
    }


}

