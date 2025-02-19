package school57kotlin2.demo

import java.sql.DriverManager
import java.util.*
import kotlin.reflect.full.primaryConstructor


fun main() {


    val url = "jdbc:postgresql://localhost:5433/postgres"
    val props = Properties().apply {
        setProperty("user", "myuser")
        setProperty("password", "mypassword")
    }

    DriverManager.getConnection(url, props).use { connection ->
        println(connection.metaData.databaseProductVersion)

        connection.createStatement().use { statement ->
            val rs = statement.executeQuery("SELECT 1+1;")

            rs.next()
            println(rs.getObject(1))
        }

        connection.createStatement().use { statement ->
            statement.executeUpdate(
                """DROP TABLE IF EXISTS users;
            """)
        }

        connection.createStatement().use { statement ->
            val rs = statement.executeUpdate(
                """create table users (
                id bigserial primary key,
                nickname varchar(255) not null,
                tg varchar(255) not null
            );
            """.trimMargin()
            )
        }

        connection.createStatement().use { statement ->
            statement.executeUpdate("""INSERT INTO users (id, nickname, tg)
                VALUES (123, 'echekere', 'genokery');
                """)
            statement.executeUpdate("""INSERT INTO users (id, nickname, tg)
                VALUES (234, 'russia', 'skibidi');
                """)
            statement.executeUpdate("""INSERT INTO users (id, nickname, tg)
                VALUES (345, 'think', 'aboutit');
                """)
        }

        connection.createStatement().use { statement ->
            val rs = statement.executeQuery("""SELECT * FROM USERS WHERE id = 123;""")

            val constr = User::class.primaryConstructor!!
            rs.next()
            val user = constr.call(rs.getLong("id"), rs.getString("nickname"), rs.getString("tg"))
            println(user.id)
            println(user.nickname)
            println(user.tg)
        }
    }
}


data class User(
    val id : Long,
    val nickname : String,
    val tg : String
)


