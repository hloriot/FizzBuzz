package com.hloriot.fizzbuzz.presentation.form

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hloriot.fizzbuzz.R
import com.hloriot.fizzbuzz.presentation.theme.FizzBuzzTheme

@Composable
private fun FormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    errorMessage: String,
    modifier: Modifier = Modifier,
    isNumberOnly: Boolean = false,
    isLastFormField: Boolean = false
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier.semantics { contentDescription = label },
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = errorMessage.isNotEmpty(),
        supportingText = if (errorMessage.isNotEmpty()) {
            { Text(errorMessage) }
        } else {
            null
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isNumberOnly) {
                KeyboardType.Number
            } else {
                KeyboardType.Text
            },
            imeAction = if (isLastFormField) {
                ImeAction.Done
            } else {
                ImeAction.Next
            }
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )
    )
}

@Composable
@VisibleForTesting
fun FizzBuzzForm(
    state: FizzBuzzFormState,
    onFizzWordValueChange: (String) -> Unit,
    onFizzMultipleValueChange: (String) -> Unit,
    onBuzzWordValueChange: (String) -> Unit,
    onBuzzMultipleValueChange: (String) -> Unit,
    onLimitValueChange: (String) -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FormField(
            value = state.fizzWord,
            onValueChange = onFizzWordValueChange,
            label = stringResource(id = R.string.fizzWordLabel),
            errorMessage = state.fizzWordError
        )
        FormField(
            value = state.fizzMultiple,
            onValueChange = onFizzMultipleValueChange,
            label = stringResource(id = R.string.fizzMultipleLabel),
            errorMessage = state.fizzMultipleError,
            isNumberOnly = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        FormField(
            value = state.buzzWord,
            onValueChange = onBuzzWordValueChange,
            label = stringResource(id = R.string.buzzWordLabel),
            errorMessage = state.buzzWordError
        )
        FormField(
            value = state.buzzMultiple,
            onValueChange = onBuzzMultipleValueChange,
            label = stringResource(id = R.string.buzzMultipleLabel),
            errorMessage = state.buzzMultipleError,
            isNumberOnly = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        FormField(
            value = state.limit,
            onValueChange = onLimitValueChange,
            label = stringResource(id = R.string.limitLabel),
            errorMessage = state.limitError,
            isNumberOnly = true,
            isLastFormField = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = onConfirmClick,
            enabled = state.confirmEnabled
        ) {
            Text(stringResource(id = R.string.confirm))
        }
    }
}

@Composable
fun FizzBuzzForm(
    modifier: Modifier = Modifier,
    onFormCompleted: (FizzBuzzFormInputs) -> Unit,
    viewModel: FizzBuzzFormViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                FizzBuzzFormViewModel(
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                )
            }
        }
    )
) {
    val state by viewModel.uiState.collectAsState()

    FizzBuzzForm(
        modifier = modifier,
        state = state,
        onFizzWordValueChange = viewModel::onFizzWordValueChange,
        onFizzMultipleValueChange = viewModel::onFizzMultipleValueChange,
        onBuzzWordValueChange = viewModel::onBuzzWordValueChange,
        onBuzzMultipleValueChange = viewModel::onBuzzMultipleValueChange,
        onLimitValueChange = viewModel::onLimitValueChange,
        onConfirmClick = { viewModel.onConfirmClick(onFormCompleted) }
    )
}

@Preview(
    name = "Default",
    showBackground = true
)
@Composable
fun GreetingPreview() {
    FizzBuzzTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            FizzBuzzForm(
                state = FizzBuzzFormState(),
                onFizzWordValueChange = {},
                onFizzMultipleValueChange = {},
                onBuzzWordValueChange = {},
                onBuzzMultipleValueChange = {},
                onLimitValueChange = {},
                onConfirmClick = {}
            )
        }
    }
}