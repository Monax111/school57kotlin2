package school57kotlin2.demo.service

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.util.*


@Component
class SecondService(
    val random: Random
) {

    fun getNextInt() = random.nextInt()

}

@Configuration
class AppConfig {

    @Bean
    fun random() = Random()
}