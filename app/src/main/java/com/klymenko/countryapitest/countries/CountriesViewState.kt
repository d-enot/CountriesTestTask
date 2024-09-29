package com.klymenko.countryapitest.countries

import com.klymenko.countryapitest.domain.Country

data class CountriesViewState(
    val countries: List<Country> = listOf(),
    val loading: Boolean = false,
    val errorMessage: String = ""
)
