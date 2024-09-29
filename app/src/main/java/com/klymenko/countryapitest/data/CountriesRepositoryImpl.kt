package com.klymenko.countryapitest.data

import com.klymenko.countryapitest.data.network.NetworkDataSource
import com.klymenko.countryapitest.data.network.toCountry
import com.klymenko.countryapitest.domain.CountriesRepository
import com.klymenko.countryapitest.domain.Country
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val networkDataSource: NetworkDataSource
) : CountriesRepository {
    override fun getCountries(): Flow<List<Country>> = flow {
        emit(
            networkDataSource.getCountries().map { item -> item.toCountry() }
        )
    }.flowOn(ioDispatcher)
}