package com.hloriot.fizzbuzz.presentation.form

import android.app.Application
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hloriot.fizzbuzz.R
import com.hloriot.fizzbuzz.domain.model.Buzz
import com.hloriot.fizzbuzz.domain.model.Fizz
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.justRun
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class FizzBuzzFormViewModelTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    private val context: Application
        get() = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application

    @MockK
    private lateinit var onFormCompleted: (FizzBuzzFormInputs) -> Unit

    @Test
    fun testOnFizzWordValueChange() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onFizzWordValueChange is called with "first"
        viewModel.onFizzWordValueChange("first")

        // Then uiState should be:
        // fizzWord = "first"
        // fizzWordError = ""
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals("first", currentUiState.fizzWord)
        assertEquals("", currentUiState.fizzWordError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnFizzWordValueChangeError() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onFizzWordValueChange is called with an empty string
        viewModel.onFizzWordValueChange("")

        // Then uiState should be:
        // fizzWord = ""
        // fizzWordError = "The word cannot be empty"
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals("", currentUiState.fizzWord)
        assertEquals(context.getString(R.string.emptyWordError), currentUiState.fizzWordError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnFizzWordValueChangeConfirmEnabled() = runTest {
        // Given a FizzBuzzFormViewModel viewModel with:
        // fizzMultiple = "2"
        // buzzWord = "second"
        // buzzMultiple = "3"
        // limit = "10"
        val viewModel = FizzBuzzFormViewModel(
            context,
            FizzBuzzFormState(
                fizzMultiple = "2",
                buzzWord = "second",
                buzzMultiple = "3",
                limit = "10"
            )
        )

        // When onFizzWordValueChange is called with "first"
        viewModel.onFizzWordValueChange("first")

        // Then uiState should be:
        // fizzWord = "first"
        // fizzWordError = ""
        // confirmEnabled = true
        val currentUiState = viewModel.uiState.value
        assertEquals("first", currentUiState.fizzWord)
        assertEquals("", currentUiState.fizzWordError)
        assertEquals(true, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnFizzMultipleValueChange() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onFizzMultipleValueChange is called with "2"
        viewModel.onFizzMultipleValueChange("2")

        // Then uiState should be:
        // fizzMultiple = "2"
        // fizzMultipleError = ""
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals("2", currentUiState.fizzMultiple)
        assertEquals("", currentUiState.fizzMultipleError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnFizzMultipleValueChangeErrorEmpty() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onFizzMultipleValueChange is called with an empty string
        viewModel.onFizzMultipleValueChange("")

        // Then uiState should be:
        // fizzMultiple = ""
        // fizzMultipleError = "A number must be specified"
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals("", currentUiState.fizzMultiple)
        assertEquals(context.getString(R.string.emptyNumberError), currentUiState.fizzMultipleError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnFizzMultipleValueChangeErrorNotNumber() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onFizzMultipleValueChange is called with "multiple"
        viewModel.onFizzMultipleValueChange("multiple")

        // Then uiState should be:
        // fizzMultiple = "multiple"
        // fizzMultipleError = "The number must be strictly positive"
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals("multiple", currentUiState.fizzMultiple)
        assertEquals(context.getString(R.string.negativeNumberError), currentUiState.fizzMultipleError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnFizzMultipleValueChangeErrorNumberTooHigh() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onFizzMultipleValueChange is called with a number superior than Long.MAX_VALUE
        viewModel.onFizzMultipleValueChange(Long.MAX_VALUE.toString() + "1")

        // Then uiState should be:
        // fizzMultiple = Long.MAX_VALUE.toString() + "1"
        // fizzMultipleError = "The number is too high"
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals(Long.MAX_VALUE.toString() + "1", currentUiState.fizzMultiple)
        assertEquals(context.getString(R.string.numberTooHighError), currentUiState.fizzMultipleError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnFizzMultipleValueChangeErrorNegativeNumber() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onFizzMultipleValueChange is called with "-1"
        viewModel.onFizzMultipleValueChange("-1")

        // Then uiState should be:
        // fizzMultiple = "-1"
        // fizzMultipleError = "The number must be strictly positive"
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals("-1", currentUiState.fizzMultiple)
        assertEquals(context.getString(R.string.negativeNumberError), currentUiState.fizzMultipleError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnFizzMultipleValueChangeConfirmEnabled() = runTest {
        // Given a FizzBuzzFormViewModel viewModel with:
        // fizzWord = "first"
        // buzzWord = "second"
        // buzzMultiple = "3"
        // limit = "10"
        val viewModel = FizzBuzzFormViewModel(
            context,
            FizzBuzzFormState(
                fizzWord = "first",
                buzzWord = "second",
                buzzMultiple = "3",
                limit = "10"
            )
        )

        // When onFizzMultipleValueChange is called with "2"
        viewModel.onFizzMultipleValueChange("2")

        // Then uiState should be:
        // fizzMultiple = "2"
        // fizzMultipleError = ""
        // confirmEnabled = true
        val currentUiState = viewModel.uiState.value
        assertEquals("2", currentUiState.fizzMultiple)
        assertEquals("", currentUiState.fizzMultipleError)
        assertEquals(true, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnBuzzWordValueChange() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onBuzzWordValueChange is called with "second"
        viewModel.onBuzzWordValueChange("second")

        // Then uiState should be:
        // buzzWord = "second"
        // buzzWordError = ""
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals("second", currentUiState.buzzWord)
        assertEquals("", currentUiState.buzzWordError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnBuzzWordValueChangeError() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onBuzzWordValueChange is called with an empty string
        viewModel.onBuzzWordValueChange("")

        // Then uiState should be:
        // buzzWord = ""
        // buzzWordError = "The word cannot be empty"
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals("", currentUiState.buzzWord)
        assertEquals(context.getString(R.string.emptyWordError), currentUiState.buzzWordError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnBuzzWordValueChangeConfirmEnabled() = runTest {
        // Given a FizzBuzzFormViewModel viewModel with:
        // fizzWord = "second"
        // fizzMultiple = "2"
        // buzzMultiple = "3"
        // limit = "10"
        val viewModel = FizzBuzzFormViewModel(
            context,
            FizzBuzzFormState(
                fizzWord = "second",
                fizzMultiple = "2",
                buzzMultiple = "3",
                limit = "10"
            )
        )

        // When onBuzzWordValueChange is called with "second"
        viewModel.onBuzzWordValueChange("second")

        // Then uiState should be:
        // buzzWord = "second"
        // buzzWordError = ""
        // confirmEnabled = true
        val currentUiState = viewModel.uiState.value
        assertEquals("second", currentUiState.buzzWord)
        assertEquals("", currentUiState.buzzWordError)
        assertEquals(true, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnBuzzMultipleValueChange() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onBuzzMultipleValueChange is called with "3"
        viewModel.onBuzzMultipleValueChange("3")

        // Then uiState should be:
        // buzzMultiple = "3"
        // buzzMultipleError = ""
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals("3", currentUiState.buzzMultiple)
        assertEquals("", currentUiState.buzzMultipleError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnBuzzMultipleValueChangeErrorEmpty() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onBuzzMultipleValueChange is called with an empty string
        viewModel.onBuzzMultipleValueChange("")

        // Then uiState should be:
        // buzzMultiple = ""
        // buzzMultipleError = "A number must be specified"
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals("", currentUiState.buzzMultiple)
        assertEquals(context.getString(R.string.emptyNumberError), currentUiState.buzzMultipleError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnBuzzMultipleValueChangeErrorNotNumber() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onBuzzMultipleValueChange is called with "multiple"
        viewModel.onBuzzMultipleValueChange("multiple")

        // Then uiState should be:
        // buzzMultiple = "multiple"
        // buzzMultipleError = "The number must be strictly positive"
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals("multiple", currentUiState.buzzMultiple)
        assertEquals(context.getString(R.string.negativeNumberError), currentUiState.buzzMultipleError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnBuzzMultipleValueChangeErrorNumberTooHigh() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onBuzzMultipleValueChange is called with a number superior than Long.MAX_VALUE
        viewModel.onBuzzMultipleValueChange(Long.MAX_VALUE.toString() + "1")

        // Then uiState should be:
        // buzzMultiple = Long.MAX_VALUE.toString() + "1"
        // buzzMultipleError = "The number is too high"
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals(Long.MAX_VALUE.toString() + "1", currentUiState.buzzMultiple)
        assertEquals(context.getString(R.string.numberTooHighError), currentUiState.buzzMultipleError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnBuzzMultipleValueChangeErrorNegativeNumber() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onBuzzMultipleValueChange is called with "-1"
        viewModel.onBuzzMultipleValueChange("-1")

        // Then uiState should be:
        // buzzMultiple = "-1"
        // buzzMultipleError = "The number must be strictly positive"
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals("-1", currentUiState.buzzMultiple)
        assertEquals(context.getString(R.string.negativeNumberError), currentUiState.buzzMultipleError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnBuzzMultipleValueChangeConfirmEnabled() = runTest {
        // Given a FizzBuzzFormViewModel viewModel with:
        // fizzWord = "first"
        // fizzMultiple = "2"
        // buzzWord = "second"
        // limit = "10"
        val viewModel = FizzBuzzFormViewModel(
            context,
            FizzBuzzFormState(
                fizzWord = "first",
                fizzMultiple = "2",
                buzzWord = "second",
                limit = "10"
            )
        )

        // When onBuzzMultipleValueChange is called with "3"
        viewModel.onBuzzMultipleValueChange("3")

        // Then uiState should be:
        // buzzMultiple = "3"
        // buzzMultipleError = ""
        // confirmEnabled = true
        val currentUiState = viewModel.uiState.value
        assertEquals("3", currentUiState.buzzMultiple)
        assertEquals("", currentUiState.buzzMultipleError)
        assertEquals(true, currentUiState.confirmEnabled)
    }


    @Test
    fun testOnLimitValueChange() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onLimitValueChange is called with "10"
        viewModel.onLimitValueChange("10")

        // Then uiState should be:
        // limit = "10"
        // limitError = ""
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals("10", currentUiState.limit)
        assertEquals("", currentUiState.limitError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnLimitValueChangeErrorEmpty() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onLimitValueChange is called with an empty string
        viewModel.onLimitValueChange("")

        // Then uiState should be:
        // limit = ""
        // limitError = "A number must be specified"
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals("", currentUiState.limit)
        assertEquals(context.getString(R.string.emptyNumberError), currentUiState.limitError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnLimitValueChangeErrorNotNumber() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onLimitValueChange is called with "limit"
        viewModel.onLimitValueChange("limit")

        // Then uiState should be:
        // limit = "limit"
        // limitError = "The number must be strictly positive"
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals("limit", currentUiState.limit)
        assertEquals(context.getString(R.string.negativeNumberError), currentUiState.limitError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnLimitValueChangeErrorNumberTooHigh() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onLimitValueChange is called with "10001"
        viewModel.onLimitValueChange("10001")

        // Then uiState should be:
        // limit = "10001"
        // limitError = "The number is too high"
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals("10001", currentUiState.limit)
        assertEquals(context.getString(R.string.numberTooHighError), currentUiState.limitError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnLimitValueChangeErrorNegativeNumber() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)

        // When onLimitValueChange is called with "-1"
        viewModel.onLimitValueChange("-1")

        // Then uiState should be:
        // limit = "-1"
        // limitError = "The number must be strictly positive"
        // confirmEnabled = false
        val currentUiState = viewModel.uiState.value
        assertEquals("-1", currentUiState.limit)
        assertEquals(context.getString(R.string.negativeNumberError), currentUiState.limitError)
        assertEquals(false, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnLimitValueChangeConfirmEnabled() = runTest {
        // Given a FizzBuzzFormViewModel viewModel with:
        // fizzWord = "first"
        // fizzMultiple = "2"
        // buzzWord = "second"
        // buzzMultiple = "3"
        val viewModel = FizzBuzzFormViewModel(
            context,
            FizzBuzzFormState(
                fizzWord = "first",
                fizzMultiple = "2",
                buzzWord = "second",
                buzzMultiple = "3"
            )
        )

        // When onLimitValueChange is called with "10"
        viewModel.onLimitValueChange("10")

        // Then uiState should be:
        // limit = "10"
        // limitError = ""
        // confirmEnabled = true
        val currentUiState = viewModel.uiState.value
        assertEquals("10", currentUiState.limit)
        assertEquals("", currentUiState.limitError)
        assertEquals(true, currentUiState.confirmEnabled)
    }

    @Test
    fun testOnConfirmClick() = runTest {
        // Given a FizzBuzzFormViewModel viewModel with:
        // fizzWord = "first"
        // fizzMultiple = "2"
        // buzzWord = "second"
        // buzzMultiple = "3"
        // limit = "10"
        // confirmEnabled = true
        val viewModel = FizzBuzzFormViewModel(
            context,
            FizzBuzzFormState(
                fizzWord = "first",
                fizzMultiple = "2",
                buzzWord = "second",
                buzzMultiple = "3",
                limit = "10",
                confirmEnabled = true
            )
        )
        val fizzBuzzFormInputs = FizzBuzzFormInputs(
            Fizz("first", 2),
            Buzz("second", 3),
            10L
        )
        justRun { onFormCompleted.invoke(fizzBuzzFormInputs) }


        // When onConfirmClick is called with onFormCompleted
        viewModel.onConfirmClick(onFormCompleted)

        // Then onFormCompleted should be called with:
        // fizz = Fizz("first", 2),
        // buzz = Buzz("second", 3),
        // limit = 10
        verify(exactly = 1) { onFormCompleted.invoke(fizzBuzzFormInputs) }
    }

    @Test
    fun testOnConfirmClickNoInputs() = runTest {
        // Given a FizzBuzzFormViewModel viewModel
        val viewModel = FizzBuzzFormViewModel(context)
        val fizzBuzzFormInputs = FizzBuzzFormInputs(
            Fizz("first", 2),
            Buzz("second", 3),
            10L
        )
        justRun { onFormCompleted.invoke(fizzBuzzFormInputs) }


        // When onConfirmClick is called with onFormCompleted
        viewModel.onConfirmClick(onFormCompleted)

        // Then onFormCompleted should not be called
        verify(exactly = 0) { onFormCompleted.invoke(fizzBuzzFormInputs) }
    }
}