package parking

data class Car(val regNum: String, val color: String) {
    override fun toString(): String {
        return "$regNum $color"
    }
}

object Parking {
    private var spots = emptyArray<Car?>()
    var count = 0

    fun create(countSpots: Int) {
        if (countSpots >0 ) {
            count = countSpots
            spots = emptyArray()
        }
        println("Created a parking lot with $countSpots spots.")
    }
    private fun getFreeSpot(): Int? {
        for (i in spots.indices) { if (spots[i] == null) return i }
        return if (spots.size < count) {
            spots += null
            spots.lastIndex
        } else null
    }

    fun park(regNum: String, color: String) {
        val car = Car(regNum, color)
        val freeSpot = try {
            getFreeSpot()!!
        } catch (e: Exception) {
            println("Sorry, the parking lot is full.")
            return
        }
        spots[freeSpot] = car
        println("$color car parked in spot ${freeSpot + 1}.")
    }

    fun leave(spot: Int) {
        if (spots[spot - 1] != null) {
            spots[spot - 1] = null
            println("Spot $spot is free.")
        } else println("There is no car in spot $spot.")
    }

    fun status() {
        if (spots.isEmpty()) println("Parking lot is empty.")
        for(i in spots.indices) if (spots[i] != null) println("${i+1} ${spots[i]}")
    }

    fun regByColor(color: String) {
        val resultList = spots.filter { it?.color?.lowercase() == color.lowercase() }
        if (resultList.isEmpty()) println("No cars with color $color were found.") else
            println(resultList.joinToString { val regNum = it!!.regNum
                regNum
            })
    }

    fun spotByColor(color: String) {
        val resultList = mutableListOf<Int>()
        spots.forEachIndexed {index,car -> if (car?.color?.lowercase() == color.lowercase()) resultList.add(index+1) }
        if (resultList.isEmpty()) println("No cars with color $color were found.") else
            println(resultList.joinToString())
    }

    fun spotByReg(reg: String) {
        val resultList = mutableListOf<Int>()
        spots.forEachIndexed {index,car -> if (car?.regNum == reg) resultList.add(index+1) }
        if (resultList.isEmpty()) println("No cars with registration number $reg were found.") else
            println(resultList.joinToString())
    }
}

fun main() {
    while (true) {
        val input = readLine()!!.split(" ")
        try {
            if (input.first() != "exit" && input.first() != "create" && Parking.count == 0) throw Exception();
            when (input.first()) {
                "park" -> Parking.park(input[1], input[2])
                "leave" ->  Parking.leave(input[1].toInt())
                "create" -> Parking.create(input[1].toInt())
                "status" -> Parking.status()
                "exit" -> break
                "reg_by_color" -> Parking.regByColor(input[1])
                "spot_by_color" -> Parking.spotByColor(input[1])
                "spot_by_reg" -> Parking.spotByReg(input[1])
            }
        } catch (e:Exception) {
            println("Sorry, a parking lot has not been created.")
        }
    }
}
