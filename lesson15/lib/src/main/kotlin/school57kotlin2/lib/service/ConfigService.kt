package school57kotlin2.lib.service

class ConfigService(
    val configComponent: ConfigComponent
) {

    fun bar() {
        // Что-то сложное
        println(configComponent.getValue())
    }

}
