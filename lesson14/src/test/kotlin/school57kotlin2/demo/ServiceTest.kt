package school57kotlin2.demo

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest(
    properties = ["spring.profiles.аctive=15"]
)
class ServiceTest {


    @Test
    fun contextLoads() {
        Thread.sleep(3000)
    }

}