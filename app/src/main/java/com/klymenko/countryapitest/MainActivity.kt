package com.klymenko.countryapitest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.klymenko.countryapitest.countries.CountriesScreen
import com.klymenko.countryapitest.ui.theme.CountryApiTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CountryApiTestTheme {
                CountriesScreen()
            }
        }
    }
}