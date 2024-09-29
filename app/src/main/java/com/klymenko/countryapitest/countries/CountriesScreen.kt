package com.klymenko.countryapitest.countries

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.klymenko.countryapitest.domain.Country

@Composable
fun CountriesScreen(viewModel: CountriesViewModel = hiltViewModel()) {
    val viewState by viewModel.countriesViewState.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            CountriesScreen(modifier = Modifier, viewState = viewState)
        }
    }
}

@Composable
fun CountriesScreen(modifier: Modifier, viewState: CountriesViewState) {
    if (viewState.loading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (viewState.errorMessage.isNotEmpty()) {
        ErrorDialog(modifier = Modifier, viewState.errorMessage)
    }
    if (viewState.countries.isNotEmpty()) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(viewState.countries) { item: Country ->
                CountryCard(modifier = Modifier, item)
            }
        }
    }
}

@Composable
fun CountryCard(modifier: Modifier, country: Country) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(4.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = country.flagImg,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(60.dp)
                    .aspectRatio(16f / 9f)
            )
            Column(modifier = modifier.padding(start = 4.dp)) {
                Text(text = country.name, style = MaterialTheme.typography.titleLarge)
                country.capital?.let {
                    Row {
                        Text(text = "Capital:", modifier = modifier.padding(end = 4.dp))
                        Text(text = it)
                    }
                }
                Row {
                    Text(text = "Region:", modifier = modifier.padding(end = 4.dp))
                    Text(text = country.region)
                }
            }
        }
    }
}

@Composable
fun ErrorDialog(modifier: Modifier, message: String) {
    val openAlertDialog = remember { mutableStateOf(true) }
    when {
        openAlertDialog.value -> {
            Dialog(onDismissRequest = { openAlertDialog.value = false }) {
                Card(modifier = modifier
                    .fillMaxWidth()
                    .height(200.dp)) {
                    Column(
                        modifier = modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Error Message", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = modifier.padding(top = 16.dp))
                        Text(text = message)
                    }
                }
            }

        }
    }
}