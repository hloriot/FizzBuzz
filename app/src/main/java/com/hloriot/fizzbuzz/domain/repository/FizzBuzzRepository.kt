package com.hloriot.fizzbuzz.domain.repository

import androidx.annotation.IntRange
import com.hloriot.fizzbuzz.domain.model.Buzz
import com.hloriot.fizzbuzz.domain.model.Fizz
import com.hloriot.fizzbuzz.domain.model.FizzBuzz
import kotlinx.coroutines.flow.Flow

interface FizzBuzzRepository {
    fun getFizzBuzz(
        fizz: Fizz,
        buzz: Buzz,
        @IntRange(from = 0L) limit: Long = Long.MAX_VALUE
    ): Flow<FizzBuzz>
}