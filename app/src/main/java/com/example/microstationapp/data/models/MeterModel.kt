package com.example.microstationapp.data.models

data class MeterModel(
    val meterType: String = "Analog",
    var currentMeter: Double = 0.0,
)
