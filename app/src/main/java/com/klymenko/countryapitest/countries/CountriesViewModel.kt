package com.klymenko.countryapitest.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klymenko.countryapitest.data.Result
import com.klymenko.countryapitest.data.asResult
import com.klymenko.countryapitest.domain.CountriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val repository: CountriesRepository
) : ViewModel() {

    val countriesViewState: StateFlow<CountriesViewState> = countriesVewState(repository).
    stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = CountriesViewState(loading = true))
}

private fun countriesVewState(repository: CountriesRepository): Flow<CountriesViewState> {
    return repository.getCountries().asResult().map { result ->
        when (result) {
            is Result.Success -> CountriesViewState(countries = result.data)
            is Result.Error -> CountriesViewState(
                errorMessage = result.exception.message ?: "Error with no message"
            )
        }
    }
}