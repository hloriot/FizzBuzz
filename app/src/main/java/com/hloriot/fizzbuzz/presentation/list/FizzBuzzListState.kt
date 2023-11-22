package com.hloriot.fizzbuzz.presentation.list

import com.hloriot.fizzbuzz.domain.model.FizzBuzz

data class FizzBuzzListState(
    val items: List<FizzBuzz> = emptyList()
)
