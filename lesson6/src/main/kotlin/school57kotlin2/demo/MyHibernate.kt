package school57kotlin2.demo

import java.sql.Connection

class MyRepository<T : Any>(
    val connection: Connection,
) {

    init {
        //Тут можно проверить наличие таблицы
        TODO()
    }

    inline fun <reified T : Any> create(obj: T): T {
        //return T::class.primaryConstructor!!.call(....)
        // Название таблички можно брать с названия класса
        TODO()
    }

    inline fun <reified T : Any> read(id: Long): T {
        TODO()
    }

    inline fun <reified T : Any> update(obj: T): T {
        TODO()
    }

    inline fun <reified T : Any> delete(id: Long): T {
        TODO()
    }

}

fun main() {
//    val connection: Connection = TODO()
//    val repository = MyRepository<...>(connection)
//
//    val entity = repository.create(...)
//    repository.read(entity.id)
//        ..
}