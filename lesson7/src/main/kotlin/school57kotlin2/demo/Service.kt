package school57kotlin2.demo

class User(
val name: String
)

class Service(
    val validator: Validator,
    val repo: Repository
) {

    fun doSome(user: User): Boolean {
        if(validator.valid(user)){
            repo.save(user)
            return true
        }
        return false
    }
}



class Repository {

    fun save(user: User){
        TODO()
    }

}

class Validator {
    fun valid(user: User): Boolean {
        TODO("Not yet implemented")
    }
}