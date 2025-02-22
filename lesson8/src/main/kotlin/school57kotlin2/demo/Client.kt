package school57kotlin2.demo

import jakarta.persistence.*
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import kotlin.random.Random


@Entity
@Table(
    name = "Clients",
    indexes = [
        Index(columnList = "id", name = "client_id_hidx"),
        Index(columnList = "age", name = "age_hidx")
    ],
)
class Client(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val age: Int,

    @Column(nullable = true)
    val address: String? = null,

    @Column(nullable = true)
    // https://yandex.ru/maps/-/CHqoU0I0
    val job: String? = null,

    //https://ru.wikipedia.org/wiki/ISO_5218
    @Column(nullable = false)
    val sex: Int,
)

fun main() {
    val sessionFactory: SessionFactory = Configuration().configure("hibernate.cfg.xml").buildSessionFactory()
    val session = sessionFactory.openSession()
    repeat(1000) {
        session.transaction.begin()
        repeat(100) {
            session.persist(generateRandomClient())
        }
        session.transaction.commit()
    }
    session.close()
    sessionFactory.close()
}

fun example() {
    val sessionFactory: SessionFactory = Configuration().configure("hibernate.cfg.xml").buildSessionFactory()
    val session = sessionFactory.openSession()
    session.transaction.begin()

    val newClient = Client(
        id = null,
        name = "Петя",
        age = 20,
        job = "водитель",
        sex = 1,
        address = "АОА Букашка"
    )
    val otherClient = Client(
        id = null,
        name = "Маша",
        age = 60,
        job = "Гендир",
        sex = 2,
        address = "ООО ромашка"
    )

    session.persist(newClient)
    session.persist(otherClient)


    val clients: List<Client> = session.createQuery("FROM Client", Client::class.java).resultList
    clients.forEach { client ->
        println(
            "ID: " + client.id +
                    ", FirstName: " + client.name +
                    ", SecondName: " + client.address
        )
    }

    val adultClients = session.createQuery("select id from Client where age > :age", Int::class.java)
        .setParameter("age", 18)
        .resultList

    adultClients.forEach { id ->
        println("ID: $id")
    }

    session.transaction.commit()
    session.close()
    sessionFactory.close()
}


fun generateRandomClient() = Client(
    id = null,
    name = Random.nextLong().toString(),
    age = Random.nextInt(),
    job = Random.nextLong().toString(),
    sex = Random.nextInt(),
    address = Random.nextLong().toString()

)