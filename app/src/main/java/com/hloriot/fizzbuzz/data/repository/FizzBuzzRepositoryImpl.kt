package com.hloriot.fizzbuzz.data.repository

import com.hloriot.fizzbuzz.domain.model.Buzz
import com.hloriot.fizzbuzz.domain.model.Fizz
import com.hloriot.fizzbuzz.domain.model.FizzBuzz
import com.hloriot.fizzbuzz.domain.repository.FizzBuzzRepository
import com.hloriot.fizzbuzz.domain.model.InvalidLimitException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FizzBuzzRepositoryImpl(private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO): FizzBuzzRepository {
    override fun getFizzBuzz(fizz: Fizz, buzz: Buzz, limit: Long): Flow<FizzBuzz> = flow {
        if (limit < 0) {
            throw InvalidLimitException()
        }

        for(i in 1L .. limit) {
            val isFizzMultiple = i % fizz.multiple == 0L
            val isBuzzMultiple = i % buzz.multiple == 0L

            when {
                isFizzMultiple && isBuzzMultiple -> emit(FizzBuzz(fizz, buzz))
                isFizzMultiple -> emit(FizzBuzz(fizz))
                isBuzzMultiple -> emit(FizzBuzz(buzz))
                else -> emit(FizzBuzz(i))
            }
        }
    }.flowOn(ioDispatcher)
}