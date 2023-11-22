package com.hloriot.fizzbuzz.presentation.form

import android.app.Application
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.hloriot.fizzbuzz.R
import com.hloriot.fizzbuzz.domain.model.Buzz
import com.hloriot.fizzbuzz.domain.model.Fizz
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FizzBuzzFormViewModel(
    private val context: Application,
    defaultState: FizzBuzzFormState = FizzBuzzFormState()
) : ViewModel() {
    private val _uiState = MutableStateFlow(defaultState)
    val uiState: StateFlow<FizzBuzzFormState> = _uiState.asStateFlow()

    private fun shouldConfirmBeEnabled(state: FizzBuzzFormState): Boolean {
        return state.fizzWord.isNotEmpty()
                && state.fizzMultiple.isNotEmpty()
                && state.buzzWord.isNotEmpty()
                && state.buzzMultiple.isNotEmpty()
                && state.limit.isNotEmpty()
                && state.fizzWordError.isEmpty()
                && state.fizzMultipleError.isEmpty()
                && state.buzzWordError.isEmpty()
                && state.buzzMultipleError.isEmpty()
                && state.limitError.isEmpty()
    }

    private fun getErrorMessageFor(number: String, maxValue: Long? = null): String {
        val value = number.toLongOrNull()

        return when {
            number.isEmpty() -> context.getString(R.string.emptyNumberError)
            !number.isDigitsOnly() -> context.getString(R.string.negativeNumberError)
            value == null -> context.getString(R.string.numberTooHighError)
            maxValue != null && value > maxValue -> context.getString(R.string.numberTooHighError)
            value <= 0 -> context.getString(R.string.negativeNumberError)
            else -> ""
        }
    }

    fun onFizzWordValueChange(word: String) {
        _uiState.update { currentState ->
            val newState = currentState.copy(
                fizzWord = word,
                fizzWordError = if (word.isEmpty()) {
                    context.getString(R.string.emptyWordError)
                } else {
                    ""
                }
            )

            newState.copy(
                confirmEnabled = shouldConfirmBeEnabled(newState)
            )
        }
    }

    fun onFizzMultipleValueChange(multiple: String) {
        _uiState.update { currentState ->
            val newState = currentState.copy(
                fizzMultiple = multiple,
                fizzMultipleError = getErrorMessageFor(multiple)
            )

            newState.copy(
                confirmEnabled = shouldConfirmBeEnabled(newState)
            )
        }
    }

    fun onBuzzWordValueChange(word: String) {
        _uiState.update { currentState ->
            val newState = currentState.copy(
                buzzWord = word,
                buzzWordError = if (word.isEmpty()) {
                    context.getString(R.string.emptyWordError)
                } else {
                    ""
                }
            )

            newState.copy(
                confirmEnabled = shouldConfirmBeEnabled(newState)
            )
        }
    }

    fun onBuzzMultipleValueChange(multiple: String) {
        _uiState.update { currentState ->
            val newState = currentState.copy(
                buzzMultiple = multiple,
                buzzMultipleError = getErrorMessageFor(multiple)
            )

            newState.copy(
                confirmEnabled = shouldConfirmBeEnabled(newState)
            )
        }
    }

    fun onLimitValueChange(limit: String) {
        _uiState.update { currentState ->
            val newState = currentState.copy(
                limit = limit,
                limitError = getErrorMessageFor(limit, 10_000)
            )

            newState.copy(
                confirmEnabled = shouldConfirmBeEnabled(newState)
            )
        }
    }

    fun onConfirmClick(onFormCompleted: (FizzBuzzFormInputs) -> Unit) {
        val state = _uiState.value

        if (!shouldConfirmBeEnabled(state)) {
            return
        }

        onFormCompleted(
            FizzBuzzFormInputs(
                Fizz(state.fizzWord, state.fizzMultiple.toLong()),
                Buzz(state.buzzWord, state.buzzMultiple.toLong()),
                state.limit.toLong()
            )
        )
    }
}