package com.klymenko.countryapitest.domain

data class Country(
    val name: String,
    val currency: String?,
    val capital: String?,
    val region: String,
    val population: Int,
    val flagImg: String?
)
