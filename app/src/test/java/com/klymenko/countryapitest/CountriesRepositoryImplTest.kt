package com.klymenko.countryapitest

import com.klymenko.countryapitest.data.CountriesRepositoryImpl
import com.klymenko.countryapitest.data.network.CountryNetwork
import com.klymenko.countryapitest.data.network.NameNetwork
import com.klymenko.countryapitest.data.network.NetworkDataSource
import com.klymenko.countryapitest.data.network.toCountry
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CountriesRepositoryImplTest {
    private lateinit var countriesRepository: CountriesRepositoryImpl
    private val networkDataSource = mockk<NetworkDataSource>()

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)

    @BeforeEach
    fun setup() {
        countriesRepository = CountriesRepositoryImpl(testDispatcher, networkDataSource)
    }

    @Test
    fun `getCountries should emit list of countries`() = runTest(testScheduler) {
        // Given
        val countryNetworkList = listOf(
            CountryNetwork(
                name = NameNetwork(common = "USA"),
                currencies = null,
                capital = null,
                region = "Americas",
                population = 331000000,
                flags = null
            ),
            CountryNetwork(
                name = NameNetwork(common = "Canada"),
                currencies = null,
                capital = null,
                region = "Americas",
                population = 37700000,
                flags = null
            )
        )

        val expectedCountries = countryNetworkList.map { it.toCountry() }

        coEvery { networkDataSource.getCountries() } returns countryNetworkList

        val resultFlow = countriesRepository.getCountries()

        val emittedCountries = resultFlow.toList()

        assertEquals(1, emittedCountries.size)
        assertEquals(expectedCountries, emittedCountries[0])
    }
}