package com.hloriot.fizzbuzz.presentation.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.hloriot.fizzbuzz.domain.model.Fizz
import com.hloriot.fizzbuzz.domain.model.FizzBuzz
import com.hloriot.fizzbuzz.presentation.theme.FizzBuzzTheme
import org.junit.Rule
import org.junit.Test

class FizzBuzzListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testFirstSecondAnd3() {
        // Given a state with the items "first, second, 3"
        composeTestRule.setContent {
            FizzBuzzTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FizzBuzzList(
                        FizzBuzzListState(
                            listOf(
                                FizzBuzz(Fizz("first", 2)),
                                FizzBuzz(Fizz("second", 3)),
                                FizzBuzz(3)
                            )
                        )
                    )
                }
            }
        }

        // Then the texts "first, second, 3" should be visible
        composeTestRule.onNodeWithText("first").assertIsDisplayed()
        composeTestRule.onNodeWithText("second").assertIsDisplayed()
        composeTestRule.onNodeWithText("3").assertIsDisplayed()
    }
}