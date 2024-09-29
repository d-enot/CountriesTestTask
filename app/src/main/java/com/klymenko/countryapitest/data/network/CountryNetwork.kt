package com.klymenko.countryapitest.data.network

import com.klymenko.countryapitest.domain.Country

data class CountryNetwork(
    val name: NameNetwork,
    val currencies: Map<String, Currency>?,
    val capital: List<String>?,
    val region: String,
    val population: Int,
    val flags: Flag?
)

data class NameNetwork(val common: String)

data class Currency(val name: String)

data class Flag(val png: String)

fun CountryNetwork.toCountry(): Country {
  return Country(
      name = name.common,
      currency = currencies?.values?.first()?.name,
      capital = capital?.first(),
      region = region,
      population = population,
      flagImg = flags?.png
  )
}