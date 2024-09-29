package com.klymenko.countryapitest

import com.klymenko.countryapitest.data.network.CountriesAPI
import com.klymenko.countryapitest.data.network.CountryNetwork
import com.klymenko.countryapitest.data.network.NameNetwork
import com.klymenko.countryapitest.data.network.NetworkDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NetworkDataSourceTest {
    private lateinit var networkDataSource: NetworkDataSource
    private val countriesAPI = mockk<CountriesAPI>()
    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)

    @BeforeEach
    fun setup() {
        networkDataSource = NetworkDataSource(countriesAPI, testDispatcher)
    }

    @Test
    fun `getCountries should return list of countries from API`() = runTest(testScheduler) {
        val expectedCountries = listOf(
            CountryNetwork(
                name = NameNetwork(common = "USA"),
                currencies = null,
                capital = null,
                region = "america",
                population = 11,
                flags = null
            )
        )
        coEvery { countriesAPI.getCountries() } returns expectedCountries

        val result = networkDataSource.getCountries()

        assertEquals(expectedCountries, result)
        coVerify(exactly = 1) { countriesAPI.getCountries() }
    }
}