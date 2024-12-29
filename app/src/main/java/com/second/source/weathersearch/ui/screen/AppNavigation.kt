package com.second.source.weathersearch.ui.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.second.source.weathersearch.ui.viewmodel.WeatherSearchViewModel

@Composable
fun AppNavigation(
    viewModel: WeatherSearchViewModel = hiltViewModel(),
    onException: (Exception) -> Unit,
    onPermissionDenied: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onException = onException,
                onPermissionDenied = onPermissionDenied
            )
        }
    }
}