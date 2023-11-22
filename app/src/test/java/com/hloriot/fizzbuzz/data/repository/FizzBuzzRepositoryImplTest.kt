package com.hloriot.fizzbuzz.data.repository

import com.hloriot.fizzbuzz.domain.model.Buzz
import com.hloriot.fizzbuzz.domain.model.Fizz
import com.hloriot.fizzbuzz.domain.model.InvalidLimitException
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class FizzBuzzRepositoryImplTest {

    @Test
    fun testFirst2Second3Limit10() = runTest {
        // Given a FizzBuzzRepository repository
        val repository = FizzBuzzRepositoryImpl()

        // When getFizzBuzz is called with:
        // the word "fizz" and a multiple of 2,
        // the word "buzz" and a multiple of 3 and
        // a limit of 10
        val fizz = Fizz("first", 2)
        val buzz = Buzz("second", 3)
        val flow = repository.getFizzBuzz(
            fizz = fizz,
            buzz = buzz,
            limit = 10L
        )

        // Then the result should be:
        // 1
        // first
        // second
        // first
        // 5
        // firstsecond
        // 7
        // first
        // second
        // first
        assertEquals(
            listOf(
                "1",
                "first",
                "second",
                "first",
                "5",
                "firstsecond",
                "7",
                "first",
                "second",
                "first",
            ),
            flow.toList().map { it.toString() }
        )
    }

    @Test
    fun testNegativeLimit() = runTest {
        // Given a FizzBuzzRepository repository
        val repository = FizzBuzzRepositoryImpl()

        // When getFizzBuzz is called with:
        // the word "fizz" and a multiple of 2,
        // the word "buzz" and a multiple of 3 and
        // a limit of -10
        val fizz = Fizz("first", 2)
        val buzz = Buzz("second", 3)
        val flow = repository.getFizzBuzz(
            fizz = fizz,
            buzz = buzz,
            limit = -10L
        )

        // Then the InvalidLimitException must be thrown
        try {
            flow.toList()
            fail("Test did not throw an InvalidLimitException")
        } catch (_: InvalidLimitException) {

        }
    }
}