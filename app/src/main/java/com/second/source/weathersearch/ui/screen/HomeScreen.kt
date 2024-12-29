package com.second.source.weathersearch.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.second.source.weathersearch.R
import com.second.source.weathersearch.datamodel.ext.getCurrentLocation
import com.second.source.weathersearch.datamodel.ext.hasInternet
import com.second.source.weathersearch.ui.viewmodel.WeatherSearchViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: WeatherSearchViewModel,
    onException: (Exception) -> Unit,
    onPermissionDenied: () -> Unit
) {
    val uiState = viewModel.weatherScreenState
    val requestLocationPermission = uiState.locationPermissionRequired
    val weatherInformation = uiState.weatherInformation

    if (!LocalContext.current.hasInternet()) {
        viewModel.getLocalWeatherData()
    }

    val currentLocation = uiState.currentLocation
    val exception = uiState.exception

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp))
                        }

                        if (currentLocation != null) {
                            IconButton(onClick = {
                                viewModel.getWeatherByLocation(
                                    currentLocation.latitude,
                                    currentLocation.longitude
                                )
                            }) {
                                Image(
                                    painterResource(R.drawable.my_location_icon),
                                    contentDescription = "location",
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Check and request location permission
            if (requestLocationPermission) {
                LocationPermissionScreen(
                    onLocationFound = { location ->
                        viewModel.updateLocationPermissionGranted()
                        viewModel.updateLocation(location)
                    }, permissionDenied = {
                        onPermissionDenied()
                    }
                )
            } else {
                if (currentLocation != null) {
                    if (exception != null) {
                        onException(exception)

                        viewModel.handledException()
                    } else {
                        if (weatherInformation != null) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                SearchScreen {
                                    viewModel.getWeatherByLocationName(it)
                                }

                                WeatherScreen(
                                    cityName = weatherInformation.cityName,
                                    temperature = weatherInformation.temperature,
                                    description = weatherInformation.description,
                                    weatherIcon = weatherInformation.weatherIcon ?: "",
                                    pressure = weatherInformation.pressure,
                                    feelsLike = weatherInformation.feelsLike,
                                    humidity = weatherInformation.humidity
                                )
                            }
                        } else {
                            viewModel.getWeatherByLocation(
                                currentLocation.latitude,
                                currentLocation.longitude
                            )
                        }
                    }
                } else {
                    LocalContext.current.getCurrentLocation { lat, long ->
                        viewModel.updateLocation(LatLng(lat, long))
                    }
                }
            }
        }
    }
}