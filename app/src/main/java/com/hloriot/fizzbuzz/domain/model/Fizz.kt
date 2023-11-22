package com.hloriot.fizzbuzz.domain.model

data class Fizz(val word: String, val multiple: Long) {
    init {
        require(word.isNotEmpty()) {
            "Fizz word cannot be empty"
        }
        require(multiple > 0) {
            "Fizz multiple must be strictly positive"
        }
    }
}
