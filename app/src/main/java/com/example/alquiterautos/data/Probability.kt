package com.example.alquiterautos.data

object Probability {
    var COST_PER_RENT: Int = 350
    var COST_PER_NO_RENT: Int = 200
    var IDLE: Int = 50

    fun getNumberCarsRentedPerDay (probability: Double): Int {
        return when(probability) {
            in 0.0..0.1 -> 0
            in 0.1..0.2 -> 1
            in 0.2..0.45 -> 2
            in 0.45..0.75 -> 3
            else -> 4
        }
    }

    fun getNumberDaysRentedPerCar (probability: Double): Int {
        return when(probability) {
            in 0.0..0.4 -> 1
            in 0.4..0.75 -> 2
            in 0.75..0.9 -> 3
            else -> 4
        }
    }
}