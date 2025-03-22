package school57kotlin2.demo.service

import java.util.*


//@Component
class SecondService(
    val random: Random
) {

    fun getNextInt() = random.nextInt()

}
