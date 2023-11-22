package com.hloriot.fizzbuzz.presentation.form

data class FizzBuzzFormState(
    val fizzWord: String = "",
    val fizzWordError: String = "",

    val fizzMultiple: String = "",
    val fizzMultipleError: String = "",

    val buzzWord: String = "",
    val buzzWordError: String = "",

    val buzzMultiple: String = "",
    val buzzMultipleError: String = "",

    val limit: String = "",
    val limitError: String = "",

    val confirmEnabled: Boolean = false
)
