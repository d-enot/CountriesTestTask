package com.klymenko.countryapitest.data.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val countriesAPI: CountriesAPI,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getCountries(): List<CountryNetwork> = withContext(ioDispatcher) {
        countriesAPI.getCountries()
    }
}