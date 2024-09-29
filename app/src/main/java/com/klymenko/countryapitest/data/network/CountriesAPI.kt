package com.klymenko.countryapitest.data.network

import retrofit2.http.GET

interface CountriesAPI {
    @GET("v3.1/all")
    suspend fun getCountries(): List<CountryNetwork>
}