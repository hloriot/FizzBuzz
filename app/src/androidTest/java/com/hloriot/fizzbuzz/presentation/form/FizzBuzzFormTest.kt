package com.hloriot.fizzbuzz.presentation.form

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import com.hloriot.fizzbuzz.R
import com.hloriot.fizzbuzz.presentation.extension.assertEditableText
import com.hloriot.fizzbuzz.presentation.extension.assertNotEditableText
import com.hloriot.fizzbuzz.presentation.theme.FizzBuzzTheme
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.justRun
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class FizzBuzzFormTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    @MockK
    lateinit var viewModel: FizzBuzzFormViewModel

    private val context: Application
        get() = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application

    private fun onFormCompleted(inputs: FizzBuzzFormInputs) {

    }

    @Test
    fun testFizzWord() {
        // Given a FizzBuzzForm with the fizzWord "first"
        composeTestRule.createContent(FizzBuzzFormState(fizzWord = "first"))

        // Then "first" should be displayed in "First word"
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.fizzWordLabel))
            .assertEditableText("first")
    }

    @Test
    fun testFizzMultiple() {
        // Given a FizzBuzzForm with the fizzMultiple "2"
        composeTestRule.createContent(FizzBuzzFormState(fizzMultiple = "2"))

        // Then "2" should be displayed in "First multiple"
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.fizzMultipleLabel))
            .assertEditableText("2")
    }

    @Test
    fun testBuzzWord() {
        // Given a FizzBuzzForm with the buzzWord "Error"
        composeTestRule.createContent(FizzBuzzFormState(buzzWord = "second"))

        // Then "second" should be displayed in "Second word"
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.buzzWordLabel))
            .assertEditableText("second")
    }

    @Test
    fun testBuzzMultiple() {
        // Given a FizzBuzzForm with the buzzMultiple "3"
        composeTestRule.createContent(FizzBuzzFormState(buzzMultiple = "3"))

        // Then "3" should be displayed in "Second multiple"
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.buzzMultipleLabel))
            .assertEditableText("3")
    }

    @Test
    fun testLimit() {
        // Given a FizzBuzzForm with the limit "10"
        composeTestRule.createContent(FizzBuzzFormState(limit = "10"))

        // Then "10" should be displayed in "Limit"
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.limitLabel))
            .assertEditableText("10")
    }

    @Test
    fun testFizzWordError() {
        // Given a FizzBuzzForm with the fizzWordError "Error"
        composeTestRule.createContent(FizzBuzzFormState(fizzWordError = "Error"))

        // Then "Error" should be displayed under "First word"
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.fizzWordLabel))
            .assertNotEditableText("Error")
    }

    @Test
    fun testFizzMultipleError() {
        // Given a FizzBuzzForm with the fizzMultipleError "Error"
        composeTestRule.createContent(FizzBuzzFormState(fizzMultipleError = "Error"))

        // Then "Error" should be displayed under "First multiple"
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.fizzMultipleLabel))
            .assertNotEditableText("Error")
    }

    @Test
    fun testBuzzWordError() {
        // Given a FizzBuzzForm with the buzzWordError "Error"
        composeTestRule.createContent(FizzBuzzFormState(buzzWordError = "Error"))

        // Then "Error" should be displayed under "Second word"
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.buzzWordLabel))
            .assertNotEditableText("Error")
    }

    @Test
    fun testBuzzMultipleError() {
        // Given a FizzBuzzForm with the buzzMultipleError "Error"
        composeTestRule.createContent(FizzBuzzFormState(buzzMultipleError = "Error"))

        // Then "Error" should be displayed under "Second multiple"
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.buzzMultipleLabel))
            .assertNotEditableText("Error")
    }

    @Test
    fun testLimitError() {
        // Given a FizzBuzzForm with the limitError "Error"
        composeTestRule.createContent(FizzBuzzFormState(limitError = "Error"))

        // Then "Error" should be displayed under "Limit"
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.limitLabel))
            .assertNotEditableText("Error")
    }

    @Test
    fun testOnFizzWordValueChangeCalled() {
        // Given a FizzBuzzForm
        composeTestRule.createContent()
        justRun { viewModel.onFizzWordValueChange("first") }

        // When entering "first" into First word
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.fizzWordLabel))
            .performTextInput("first")

        // Then onFizzWordValueChange function should be called with "first"
        verify(exactly = 1) { viewModel.onFizzWordValueChange("first") }
        confirmVerified(viewModel)
    }

    @Test
    fun testOnFizzMultipleValueChangeCalled() {
        // Given a FizzBuzzForm
        composeTestRule.createContent()
        justRun { viewModel.onFizzMultipleValueChange("2") }

        // When entering "2" into First multiple
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.fizzMultipleLabel))
            .performTextInput("2")

        // Then onFizzMultipleValueChange function should be called with "2"
        verify(exactly = 1) { viewModel.onFizzMultipleValueChange("2") }
        confirmVerified(viewModel)
    }

    @Test
    fun testOnBuzzWordValueChangeCalled() {
        // Given a FizzBuzzForm
        composeTestRule.createContent()
        justRun { viewModel.onBuzzWordValueChange("second") }

        // When entering "second" into Second word
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.buzzWordLabel))
            .performTextInput("second")

        // Then onBuzzWordValueChange function should be called with "second"
        verify(exactly = 1) { viewModel.onBuzzWordValueChange("second") }
        confirmVerified(viewModel)
    }

    @Test
    fun testOnBuzzMultipleValueChangeCalled() {
        // Given a FizzBuzzForm
        composeTestRule.createContent()
        justRun { viewModel.onBuzzMultipleValueChange("3") }

        // When entering "3" into Second multiple
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.buzzMultipleLabel))
            .performTextInput("3")

        // Then onBuzzMultipleValueChange function should be called with "3"
        verify(exactly = 1) { viewModel.onBuzzMultipleValueChange("3") }
        confirmVerified(viewModel)
    }

    @Test
    fun testOnLimitValueChangeCalled() {
        // Given a FizzBuzzForm
        composeTestRule.createContent()
        justRun { viewModel.onLimitValueChange("10") }

        // When entering "10" into Limit
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.limitLabel))
            .performTextInput("10")

        // Then onLimitValueChange function should be called with "10"
        verify(exactly = 1) { viewModel.onLimitValueChange("10") }
        confirmVerified(viewModel)
    }

    @Test
    fun testOnConfirmClickCalled() {
        // Given a FizzBuzzForm with the confirm button enabled
        composeTestRule.createContent(
            FizzBuzzFormState(confirmEnabled = true)
        )
        justRun { viewModel.onConfirmClick(::onFormCompleted) }

        // When confirm is clicked
        composeTestRule.onNodeWithText(context.getString(R.string.confirm))
            .performClick()

        // Then confirm button should be enabled
        // And onConfirmClick function should be called
        composeTestRule.onNodeWithText(context.getString(R.string.confirm))
            .assertIsEnabled()
        verify(exactly = 1) { viewModel.onConfirmClick(::onFormCompleted) }
        confirmVerified(viewModel)
    }

    @Test
    fun testOnConfirmClickCalledFailed() {
        // Given a FizzBuzzForm with the confirm button disabled
        composeTestRule.createContent(
            FizzBuzzFormState(confirmEnabled = false)
        )
        justRun { viewModel.onConfirmClick(::onFormCompleted) }

        // When confirm is clicked
        composeTestRule.onNodeWithText(context.getString(R.string.confirm))
            .performClick()

        // Then confirm button should not be enabled
        // And onConfirmClick function should not be called
        composeTestRule.onNodeWithText(context.getString(R.string.confirm))
            .assertIsNotEnabled()
        verify(exactly = 0) { viewModel.onConfirmClick(::onFormCompleted) }
        confirmVerified(viewModel)
    }

    private fun ComposeContentTestRule.createContent(state: FizzBuzzFormState = FizzBuzzFormState()) {
        setContent {
            FizzBuzzTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FizzBuzzForm(
                        state,
                        viewModel::onFizzWordValueChange,
                        viewModel::onFizzMultipleValueChange,
                        viewModel::onBuzzWordValueChange,
                        viewModel::onBuzzMultipleValueChange,
                        viewModel::onLimitValueChange,
                        { viewModel.onConfirmClick(::onFormCompleted) }
                    )
                }
            }
        }
    }
}