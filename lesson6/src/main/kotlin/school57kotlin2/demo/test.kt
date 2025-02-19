
package school57kotlin2.demo

import java.sql.Connection
import java.sql.DriverManager
import java.util.*


fun main() {
    val user = users(id = 22, last_name = "гитлер", first_name="гойда", gender = "Male", age = 52)
    val url = "jdbc:postgresql://localhost:5432/postgres"
    val props = Properties().apply {
        setProperty("user", "myuser")
        setProperty("password", "mypassword")
    }



    val connection: Connection = DriverManager.getConnection(url, props)
    val repository = MyRepository<users>(connection, users::class)
//    val user2 = repository.create(user)
//    println(user2)
    val aaa = repository.read<users>(1)
    val bb = repository.update(user)
    val cc = repository.delete<users>(21)
    println(cc)
}
