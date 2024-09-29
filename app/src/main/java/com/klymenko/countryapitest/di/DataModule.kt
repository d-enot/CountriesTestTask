package com.klymenko.countryapitest.di

import com.klymenko.countryapitest.data.CountriesRepositoryImpl
import com.klymenko.countryapitest.data.network.CountriesAPI
import com.klymenko.countryapitest.data.network.NetworkDataSource
import com.klymenko.countryapitest.domain.CountriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideCountriesDataSource(
        countriesApi: CountriesAPI,
        ioDispatcher: CoroutineDispatcher
    ): NetworkDataSource = NetworkDataSource(countriesApi, ioDispatcher)

    @Provides
    @Singleton
    fun provideCountriesRepository(
        networkDataSource: NetworkDataSource,
        ioDispatcher: CoroutineDispatcher
    ): CountriesRepository = CountriesRepositoryImpl(ioDispatcher, networkDataSource)
}