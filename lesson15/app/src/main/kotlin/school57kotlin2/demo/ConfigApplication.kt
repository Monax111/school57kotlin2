package school57kotlin2.demo

import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import java.util.stream.Collectors



@SpringBootApplication
class ConfigApplication

fun main(args: Array<String>) {
    findAllClassesUsingReflectionsLibrary(ConfigApplication::class.java.packageName).forEach {
        println(it.name)
    }
    runApplication<ConfigApplication>(*args)
}

fun findAllClassesUsingReflectionsLibrary(packageName: String): Set<Class<*>> {
    val reflections: Reflections = Reflections(packageName)
    return reflections.getTypesAnnotatedWith(Service::class.java)
        .stream()
        .collect(Collectors.toSet()).also {
            println(it.size)
        }
}