package com.hloriot.fizzbuzz.presentation.list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hloriot.fizzbuzz.data.repository.FizzBuzzRepositoryImpl
import com.hloriot.fizzbuzz.domain.model.Buzz
import com.hloriot.fizzbuzz.domain.model.Fizz
import com.hloriot.fizzbuzz.domain.repository.FizzBuzzRepository
import com.hloriot.fizzbuzz.presentation.model.DispatchersProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FizzBuzzListViewModel(
    private val context: Application,
    private val fizz: Fizz,
    private val buzz: Buzz,
    private val limit: Long,
    private val fizzBuzzRepository: FizzBuzzRepository = FizzBuzzRepositoryImpl(),
    private val dispatchersProvider: DispatchersProvider = DispatchersProvider()
) : ViewModel() {
    private val _uiState = MutableStateFlow(FizzBuzzListState())
    val uiState: StateFlow<FizzBuzzListState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(dispatchersProvider.io) {
            fizzBuzzRepository.getFizzBuzz(
                fizz,
                buzz,
                limit
            ).collect { fizzBuzz ->
                _uiState.update { currentState ->
                    currentState.copy(items = currentState.items + fizzBuzz)
                }
            }
        }
    }
}