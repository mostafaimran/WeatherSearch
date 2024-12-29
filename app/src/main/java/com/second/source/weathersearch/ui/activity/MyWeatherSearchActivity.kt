package com.second.source.weathersearch.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.second.source.weathersearch.R
import com.second.source.weathersearch.core.base.theme.WeatherTheme
import com.second.source.weathersearch.datamodel.exceptions.LocationNotFoundException
import com.second.source.weathersearch.ui.screen.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyWeatherSearchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherTheme {
                AppNavigation(
                    onException = {
                        when (it) {
                            is LocationNotFoundException -> {
                                Toast.makeText(
                                    this,
                                    R.string.no_location_found,
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            else -> {
                                Toast.makeText(
                                    this,
                                    R.string.server_error_message,
                                    Toast.LENGTH_LONG
                                ).show()

                                finish()
                            }
                        }
                    },
                    onPermissionDenied = {
                        Toast.makeText(
                            this,
                            R.string.location_permission_issue,
                            Toast.LENGTH_LONG
                        ).show()

                        finish()
                    }
                )
            }
        }
    }
}