package school57kotlin2.lib.service

class ConfigComponent(
    val configs: ConfigProperties
){
    fun getValue(): String {
        // что-то очень важное
        return configs.getSomeConfig()
    }

}
