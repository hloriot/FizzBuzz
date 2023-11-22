package com.hloriot.fizzbuzz.domain.model

data class Buzz(val word: String, val multiple: Long) {
    init {
        require(word.isNotEmpty()) {
            "Buzz word cannot be empty"
        }
        require(multiple > 0) {
            "Buzz multiple must be strictly positive"
        }
    }
}
