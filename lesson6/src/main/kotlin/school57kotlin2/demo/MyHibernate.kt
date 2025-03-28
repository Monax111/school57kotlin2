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

        val columns = map.keys.toList().joinToString(", ")
        val values = map.values.toList().joinToString(", ") { if (it is String) "'$it'" else it.toString() }
        val query = """insert into $tableName ($columns) values($values);"""


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
            val data = statement.executeQuery("SELECT * FROM $tableName WHERE id = $id")

            if (data.next()) {
                T::class.declaredMemberProperties.forEach { properties ->
                    map[properties.name] = data.getObject(properties.name)
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

        val newValues = map.keys.joinToString(", ") { "$it = ${if (map[it] is String) "'${map[it]}'" else map[it].toString()}" }


        val query = """update  $tableName  set $newValues where id = ${map["id"]}"""


        connection.createStatement().use { statement ->
            statement.executeUpdate(query)
        }
        return obj
    }
    inline fun <reified T : Any> delete(id: Long): T {
        val obj = read<T>(id)
        val query = """delete from $tableName  where id = $id"""

        connection.createStatement().use { statement ->
            statement.executeUpdate(query)
        }
        return obj
    }

}
