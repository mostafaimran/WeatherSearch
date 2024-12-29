package com.second.source.weathersearch.datamodel.ext

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import com.second.source.weathersearch.data.Constants.KELVIN_CELSIUS_DIFF

fun Double.toCelsius(): Int {
    val celsius = this - KELVIN_CELSIUS_DIFF
    return celsius.toInt()
}


fun Context.hasInternet(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork

    val capabilities = connectivityManager.getNetworkCapabilities(network)
    var hasInternet = false
    capabilities?.let {
        hasInternet = it.hasCapability(NET_CAPABILITY_INTERNET)
    }
    return hasInternet
}
