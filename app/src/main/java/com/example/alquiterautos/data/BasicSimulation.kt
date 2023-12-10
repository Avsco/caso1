package com.example.alquiterautos.data

data class StateData (
    var daysInformation: List<List<InfoDay>>,
    var filter: Int,
    var balance: List<Int> ,
    var typeCars: List<InfoCar>,
)

class CarRental  {

    val dataState = StateData(
        listOf(listOf()),
        1,
        listOf(0, 0, 0, 0),
        listOf(
            InfoCar(price = Probability.COST_PER_RENT, model = "X", leisureCost = Probability.IDLE, availableCost = Probability.COST_PER_NO_RENT),
            InfoCar(price = Probability.COST_PER_RENT, model = "Y", leisureCost = Probability.IDLE, availableCost = Probability.COST_PER_NO_RENT),
            InfoCar(price = Probability.COST_PER_RENT, model = "Z", leisureCost = Probability.IDLE, availableCost = Probability.COST_PER_NO_RENT),
            InfoCar(price = Probability.COST_PER_RENT, model = "W", leisureCost = Probability.IDLE, availableCost = Probability.COST_PER_NO_RENT),
        )
    )

    fun updateBalance() {
        val newBalances: MutableList<Int> = mutableListOf()

        repeat(dataState.daysInformation.size) { cars ->
            val newBalance = dataState.daysInformation.get(cars)
                .fold(0) { acc, it -> it.rentGenerated + acc }

            newBalances.add(newBalance)
        }

        dataState.balance = newBalances.toList()
    }

    fun initializate() {
        val daysInformation = mutableListOf<List<InfoDay>>()
        repeat(4) { quantityCars ->

            val carInformation = mutableListOf<InfoDay>()
            repeat(365) {
                val infoPerDay = balancePerDay(quantityCars + 1)
                carInformation.add(infoPerDay)
            }

            daysInformation.add(carInformation.toList())
        }

        dataState.daysInformation  = daysInformation.toList()

        updateBalance()
    }

    private fun balancePerDay(quantityCars: Int): InfoDay {
        val randomNumber = Math.random()
        val carsToRent = Probability.getNumberCarsRentedPerDay(randomNumber)


        var penalty = 0
        repeat(-(carsToRent - quantityCars)){
            val carNoRent = dataState.typeCars[3-it].availableCost
            dataState.typeCars[3-it].accBalance -= carNoRent
            penalty += carNoRent
        }

        var rentAccumulated = 0
        var carsNoRent = 0
        var accDaysPerRent = 0

        repeat(if (carsToRent >= quantityCars) quantityCars else carsToRent) {
            val randomNumber = Math.random()
            val daysPerRent = Probability.getNumberDaysRentedPerCar(randomNumber)

            accDaysPerRent += daysPerRent
            rentAccumulated += dataState.typeCars[it].price * daysPerRent
            dataState.typeCars[it].accBalance += dataState.typeCars[it].price * daysPerRent
        }

        val balance = penalty + rentAccumulated

        return InfoDay(carsToRent, penalty, carsNoRent, balance, accDaysPerRent)
    }
}

fun main() {
    val asd = CarRental()
    asd.initializate()
    println(asd.dataState.balance)
    println(asd.dataState.typeCars)
}