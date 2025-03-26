package school57kotlin2.demo.service

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service

@Service
class MainService(
    val configService: ConfigService
) {

    @PostConstruct
    fun doSome() {
        configService.bar()
    }

}