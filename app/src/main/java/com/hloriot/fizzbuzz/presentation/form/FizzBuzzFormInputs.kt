package com.hloriot.fizzbuzz.presentation.form

import com.hloriot.fizzbuzz.domain.model.Buzz
import com.hloriot.fizzbuzz.domain.model.Fizz

data class FizzBuzzFormInputs(
    val fizz: Fizz,
    val buzz: Buzz,
    val limit: Long
)
