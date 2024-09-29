package com.klymenko.countryapitest.domain

import kotlinx.coroutines.flow.Flow

interface CountriesRepository {
    fun getCountries(): Flow<List<Country>>
}