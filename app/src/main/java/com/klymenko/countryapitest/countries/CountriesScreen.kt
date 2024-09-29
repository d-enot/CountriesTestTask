package com.klymenko.countryapitest.countries

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CountriesScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {

        }
    }
}

@Composable
fun CountriesScreen(modifier: Modifier) {

}