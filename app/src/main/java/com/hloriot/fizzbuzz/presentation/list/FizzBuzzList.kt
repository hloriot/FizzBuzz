package com.hloriot.fizzbuzz.presentation.list

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hloriot.fizzbuzz.domain.model.FizzBuzz
import com.hloriot.fizzbuzz.presentation.form.FizzBuzzFormInputs

@Composable
private fun FizzBuzzItem(fizzBuzz: FizzBuzz, modifier: Modifier = Modifier) {
    ListItem(modifier = modifier, headlineContent = {
        Text(text = fizzBuzz.toString())
    })
}


@Composable
@VisibleForTesting
fun FizzBuzzList(
    state: FizzBuzzListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(state.items) { fizzBuzz ->
            FizzBuzzItem(fizzBuzz = fizzBuzz)
            Divider()
        }
    }
}

@Composable
fun FizzBuzzList(
    inputs: FizzBuzzFormInputs,
    modifier: Modifier = Modifier,
    viewModel: FizzBuzzListViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                FizzBuzzListViewModel(
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application,
                    inputs.fizz,
                    inputs.buzz,
                    inputs.limit
                )
            }
        }
    )
) {
    val state by viewModel.uiState.collectAsState()

    FizzBuzzList(
        modifier = modifier,
        state = state
    )
}