package com.second.source.weathersearch.datamodel.ext

import com.second.source.weathersearch.data.Constants.KELVIN_CELSIUS_DIFF

fun Double.toCelsius(): Int {
    val celsius = this - KELVIN_CELSIUS_DIFF
    return celsius.toInt()
}
