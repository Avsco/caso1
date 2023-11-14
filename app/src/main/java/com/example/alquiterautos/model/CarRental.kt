package com.example.alquiterautos.model

import androidx.lifecycle.ViewModel
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
        val daysInformation: List<InfoDay> = listOf(),
        val filter: Int = 1,
        val balance: Int = 0
    )

    init {
        initializate(1)
    }

    fun updateFilter(newFilter: Int) {
        _dataState.update {
            it.copy(
                filter = newFilter
            )
        }
        initializate(newFilter)
    }

    fun updateBalance() {
        val newBalance = _dataState.value.daysInformation.fold(0) { acc, it -> it.rentGenerated + acc }

        _dataState.update {
            it.copy(
                balance = newBalance
            )
        }
    }

    fun initializate(quantityCars: Int) {
        val daysInformation = mutableListOf<InfoDay>()
        repeat(365) {
            val infoPerDay = balancePerDay(quantityCars)
            daysInformation.add(infoPerDay)
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

    fun getFullBalance(): Int {
        return dataState.value.daysInformation.fold(0) { acc, it -> it.rentGenerated + acc }
    }

    fun getADayFromInformation(day: Int): InfoDay? {
        if (dataState.value.daysInformation.size < day) return null
        return dataState.value.daysInformation.get(day - 1)
    }
}