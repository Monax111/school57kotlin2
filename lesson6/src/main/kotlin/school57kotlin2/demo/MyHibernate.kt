package school57kotlin2.demo

import sun.awt.image.PixelConverter.Argb.instance
import java.sql.Connection
import java.sql.DriverManager
import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

val url = "jdbc:postgresql://localhost:5433/postgres"
val props = Properties().apply {
    setProperty("user", "myuser")
    setProperty("password", "mypassord")
}

class MyRepository<T : Any>(
    val connection: Connection,
) {
    val table = "users"
    //TODO()
    init {
        connection.createStatement().use { statement ->
            statement.executeUpdate(
                """create if not exists table $table (
                id bigserial primary key,
                nickname varchar(255) not null,
                tg varchar(255) not null
            );
            """.trimMargin()
            )
        }
    }

    inline fun <reified T : Any> create(obj: T): T {
        var columns = mutableListOf<String>()
        var values = mutableListOf<Any?>()

        T::class.declaredMemberProperties.forEach { properties ->

            columns.add(properties.name)
            values.add(properties.get(obj))
        }

        connection.createStatement().use { statement ->

            statement.executeUpdate(
                """INSERT INTO $table ($columns)
                VALUES ($values);
                """
            )
        }
        return obj
    }

    inline fun <reified T : Any> read(id: Long): T {
        val data = mutableMapOf<String, Any?>()
        connection.createStatement().use { statement ->

            val rs = statement.executeQuery(
                """SELECT * FROM $table WHERE id = $id;
                """
            )

            rs.next()
            T::class.declaredMemberProperties.forEach { properties ->
                data[properties.name] = rs.getObject(properties.name)
            }
        }
        val constr = T::class.primaryConstructor!!

        val args = constr.parameters.associateWith { data[it.name] }

        return constr.callBy(args)
    }

    inline fun <reified T : Any> update(obj: T): T {
        val data = mutableMapOf<String, Any?>()
        T::class.declaredMemberProperties.forEach { properties ->
            data[properties.name] = properties.get(obj)
        }

        val values = data.keys.joinToString(", ") { "$it = ${if (data[it] is String) "'${data[it]}'" else data[it].toString()}" }

        connection.createStatement().use { statement ->
            statement.executeUpdate("""UPDATE $table SET $values WHERE id = ${data["id"]}""")
        }
        return obj
    }

    inline fun <reified T : Any> delete(id: Long): T {
        connection.createStatement().use { statement ->

            val rs = statement.executeUpdate(
                """DELETE FROM $table WHERE id = $id;
                """
            )
        }

        val obj = read<T>(id);
        return obj
    }
}


//fun main() {
//    val connection: Connection = TODO()
//    val repository = MyRepository<...>(connection)
//
//    val entity = repository.create(...)
//    repository.read(entity.id)
//        ..
//}