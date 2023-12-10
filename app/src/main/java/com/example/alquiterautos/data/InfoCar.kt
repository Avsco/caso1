package com.example.alquiterautos.data

data class InfoCar(
    var model: String,
    var price: Int,
    var leisureCost: Int,
    var availableCost: Int,
    var accBalance: Int = 0,
)
