package school57kotlin2.demo.configuration

open class ClientProperties {
    open lateinit var uri: String
    open var connectionTimeoutMs: Long = 200
    open var readTimeoutMs: Long = 10
}