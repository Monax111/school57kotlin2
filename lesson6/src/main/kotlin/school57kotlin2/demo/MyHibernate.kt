package school57kotlin2.demo

import java.sql.Connection
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

class MyRepository<T : Any>(
    val connection: Connection,
    type: KClass<T>,
) {
    val tableName = type.simpleName.toString()
    init {

        connection.createStatement().use { statement ->

            statement.executeQuery("""SELECT to_regclass('$tableName') IS NOT NULL AS table_exists;""").use { rs ->
                rs.next()
                if (!rs.getBoolean("table_exists") ){
                    error("Таблица не найдена")
                }
            }
        }
    }
    inline fun <reified T : Any> create(obj: T): T {
        val map = mutableMapOf<String, Any?>()
        T::class.declaredMemberProperties.forEach { properties ->
            map[properties.name] = properties.get(obj)
        }

        val query = """insert into $tableName (${map.keys.toList().joinToString(", ")}) values(${map.values.toList().joinToString(", ") { if (it is String) "'$it'" else it.toString() }});"""


        connection.createStatement().use { statement ->
            statement.executeUpdate(query)
        }
        val constructor = T::class.primaryConstructor!!

        val args = constructor.parameters.associateWith { map[it.name] }

        return constructor.callBy(args)

    }
    inline fun <reified T : Any> read(id: Long): T {
        val map = mutableMapOf<String, Any?>()

        connection.createStatement().use { statement ->
            val rs = statement.executeQuery("SELECT * FROM $tableName WHERE id = $id")

            if (rs.next()) {
                T::class.declaredMemberProperties.forEach { properties ->
                    map[properties.name] = rs.getObject(properties.name)
                }
            } else {
                error("Запись с id = $id не найдена")
            }
        }

        val constructor = T::class.primaryConstructor!!

        val args = constructor.parameters.associateWith { map[it.name] }

        return constructor.callBy(args)

    }

    inline fun <reified T : Any> update(obj: T): T {
        val map = mutableMapOf<String, Any?>()
        T::class.declaredMemberProperties.forEach { properties ->
            map[properties.name] = properties.get(obj)
        }

        val newValues = map.keys.joinToString(", ") { "$it = ${if (map[it] is String) "'${map[it]}'" else map[it].toString()}" }  // Генерируем `SET col1 = ?, col2 = ?`


        val query = """update  $tableName  set $newValues where id = ${map["id"]}"""
        println(query)


        connection.createStatement().use { statement ->
            statement.executeUpdate(query)
        }
        return obj
    }
    inline fun <reified T : Any> delete(id: Long): T {
        val user = read<T>(id)
        val query = """delete from $tableName  where id = $id"""

        connection.createStatement().use { statement ->
            statement.executeUpdate(query)
        }
        return user
    }

}
