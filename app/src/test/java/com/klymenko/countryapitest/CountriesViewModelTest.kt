package com.klymenko.countryapitest

import com.klymenko.countryapitest.countries.CountriesViewModel
import com.klymenko.countryapitest.countries.CountriesViewState
import com.klymenko.countryapitest.domain.CountriesRepository
import com.klymenko.countryapitest.domain.Country
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CountriesViewModelTest {
    private lateinit var viewModel: CountriesViewModel
    private val repository = mockk<CountriesRepository>()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading`() = testScope.runTest {
        coEvery { repository.getCountries() } returns emptyFlow()
        viewModel = CountriesViewModel(repository)
        val initialState = viewModel.countriesViewState.value

        assertEquals(true, initialState.loading)
        assertEquals(emptyList<Country>(), initialState.countries)
        assertEquals("", initialState.errorMessage)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `emits countries when repository returns data`() = testScope.runTest {
        val countryList = listOf(
            Country(
                name = "USA",
                region = "Americas",
                population = 331000000,
                capital = null,
                currency = null,
                flagImg = null
            ),
            Country(
                name = "Canada",
                region = "Americas",
                population = 37700000,
                capital = null,
                currency = null,
                flagImg = null
            )
        )
        val successFlow = flowOf(countryList)
        coEvery { repository.getCountries() } returns successFlow

        viewModel = CountriesViewModel(repository)

        val states = mutableListOf<CountriesViewState>()
        val job = launch {
            viewModel.countriesViewState.toList(states)
        }

        advanceUntilIdle()

        assertEquals(2, states.size)
        assertEquals(true, states[0].loading)
        assertEquals(countryList, states[1].countries)
        assertEquals(false, states[1].loading)
        assertEquals("", states[1].errorMessage)

        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `emits error when repository throws exception`() = testScope.runTest {
        val exception = Exception("Network error")
        val errorFlow = flow<List<Country>> { throw exception }
        coEvery { repository.getCountries() } returns errorFlow

        viewModel = CountriesViewModel(repository)

        val states = mutableListOf<CountriesViewState>()
        val job = launch {
            viewModel.countriesViewState.toList(states)
        }

        advanceUntilIdle()

        assertEquals(2, states.size)
        assertEquals(true, states[0].loading)
        assertEquals("Network error", states[1].errorMessage)
        assertEquals(false, states[1].loading)
        assertEquals(emptyList<Country>(), states[1].countries)

        job.cancel()
    }
}