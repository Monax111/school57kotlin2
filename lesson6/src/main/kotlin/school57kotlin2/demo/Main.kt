package school57kotlin2.demo

import java.sql.DriverManager
import java.util.*


fun main() {


    val url = "jdbc:postgresql://localhost:5432/postgres"
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
            statement.executeUpdate("""create table IF NOT EXISTS users
                                            (
                                                id         bigserial primary key,
                                                last_name  varchar(255) not null,
                                                first_name varchar(255),
                                                gender varchar(10),
                                                age INT

                                            );""")
            statement.executeUpdate("""create table IF NOT EXISTS reactions_type
                                            (
                                                id bigserial primary key, 
                                                type varchar,
                                                emotion_type varchar,
                                                emoji varchar
                                            );""")
            statement.executeUpdate("""create table IF NOT EXISTS reactions
                                            (
                                                id bigserial primary key,
                                                reactions_type_id bigint references reactions_type(id),
                                                sender_type_id bigint,
                                                receiver_type_id bigint
                                            );""")
//            statement.executeUpdate("""insert into public.users ( last_name, first_name,gender, age) values('тест', 'тестович', 'небинарный', 3)""")
//            statement.executeUpdate("""insert into public.users ( last_name, first_name,gender, age) values('тест2', 'тестовна', 'жен', 7)""")
//            statement.executeUpdate("""insert into public.users ( last_name, first_name,gender, age) values('тест3', 'тестов', 'муж', 52)""")
            statement.executeQuery("""SELECT to_regclass('reactions_typee') IS NOT NULL AS table_exists;""").use { rs -> rs.next(); println(rs.getObject(1)) }
//            statement.executeUpdate("""select 1 if exists goidas  then select 2""")
        }



    }


}


