package com.hloriot.fizzbuzz.presentation.list

import android.app.Application
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hloriot.fizzbuzz.domain.model.Buzz
import com.hloriot.fizzbuzz.domain.model.Fizz
import com.hloriot.fizzbuzz.domain.model.FizzBuzz
import com.hloriot.fizzbuzz.domain.repository.FizzBuzzRepository
import com.hloriot.fizzbuzz.presentation.model.DispatchersProvider
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class FizzBuzzListViewModelTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var repository: FizzBuzzRepository

    private val context: Application
        get() = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application

    private val testDispatchers = DispatchersProvider(
        Dispatchers.Unconfined,
        Dispatchers.Unconfined,
        Dispatchers.Unconfined
    )


    @Test
    fun testFirstSecondAnd3() = runTest {
        // Given a FizzBuzzListViewModel viewModel with:
        // the word "fizz" and a multiple of 2,
        // the word "buzz" and a multiple of 3,
        // a limit of 10 and
        // a FizzBuzzRepository that emits "first, second, 3"

        val fizz = Fizz("first", 2)
        val buzz = Buzz("second", 3)
        every { repository.getFizzBuzz(fizz, buzz, 10L) } returns flowOf(
            FizzBuzz(fizz),
            FizzBuzz(buzz),
            FizzBuzz(3)
        )

        val viewModel = FizzBuzzListViewModel(
            context,
            fizz,
            buzz,
            10L,
            repository,
            testDispatchers
        )

        // Then the state items should be:
        // 1
        // 2
        // 3
        val currentUiState = viewModel.uiState.value
        assertEquals(
            listOf(
                "first",
                "second",
                "3"
            ),
            currentUiState.items.map { it.toString() }
        )
    }
}