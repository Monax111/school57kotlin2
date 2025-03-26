package school57kotlin2.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ClockApplication

fun main(args: Array<String>) {
    runApplication<ClockApplication>(*args)
}