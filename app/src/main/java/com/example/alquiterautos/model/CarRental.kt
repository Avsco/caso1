package com.example.alquiterautos.model

import androidx.lifecycle.ViewModel
import com.example.alquiterautos.data.InfoCar
import com.example.alquiterautos.data.InfoDay
import com.example.alquiterautos.data.Probability
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CarRental @Inject constructor() : ViewModel() {
    private val _dataState = MutableStateFlow(DataState())
    val dataState: StateFlow<DataState> = _dataState.asStateFlow()

    data class DataState(
        val daysInformation: List<List<InfoDay>> = listOf(listOf()),
        val filter: Int = 1,
        val balance: List<Int> = listOf(0, 0, 0, 0),
        val typeCars: List<InfoCar> = listOf(
            InfoCar(price = 0, model = "", leisureCost = 0, availableCost = 0),
            InfoCar(price = 0, model = "", leisureCost = 0, availableCost = 0),
            InfoCar(price = 0, model = "", leisureCost = 0, availableCost = 0),
            InfoCar(price = 0, model = "", leisureCost = 0, availableCost = 0),
        )
    )

    init {
        initializate()
    }

    fun updateFilter(newFilter: Int) {
        _dataState.update {
            it.copy(
                filter = newFilter
            )
        }
    }

    fun updateTypeCar(index: Int, car: InfoCar) {
        _dataState.update {
            it.copy(
                typeCars = _dataState.value.typeCars.mapIndexed { i, infoCar ->
                    if(i == index) car
                    else infoCar
                }
            )
        }
    }

    fun updateBalance() {
        val newBalances: MutableList<Int> = mutableListOf()

        repeat(_dataState.value.daysInformation.size) { cars ->
            val newBalance = _dataState.value.daysInformation.get(cars)
                .fold(0) { acc, it -> it.rentGenerated + acc }

            newBalances.add(newBalance)
        }

        _dataState.update {
            it.copy(
                balance = newBalances.toList()
            )
        }
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
        _dataState.update {
            it.copy(
                daysInformation = daysInformation.toList(),
            )
        }
        updateBalance()
    }

    private fun balancePerDay(quantityCars: Int): InfoDay {
        val randomNumber = Math.random()
        val carsToRent = Probability.getNumberCarsRentedPerDay(randomNumber)

        val penalty: Int = if (carsToRent > quantityCars) {
            -(Probability.COST_PER_NO_RENT * (carsToRent - quantityCars))
        } else {
            0
        }

        var rentAccumulated = 0
        var carsNoRent = 0

        repeat(if (carsToRent >= quantityCars) quantityCars else carsToRent) {
            val randomNumber = Math.random()
            val daysPerRent = Probability.getNumberDaysRentedPerCar(randomNumber)

            if (daysPerRent === 0) {
                carsNoRent++
            }

            rentAccumulated += daysPerRent
        }

        val balance =
            penalty + (rentAccumulated * Probability.COST_PER_RENT) - (carsNoRent * Probability.IDLE)

        return InfoDay(carsToRent, penalty, carsNoRent, balance, rentAccumulated)
    }
}