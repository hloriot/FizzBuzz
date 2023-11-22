package com.hloriot.fizzbuzz.domain.model

@JvmInline
value class FizzBuzz private constructor(private val word: String) {

    constructor(fizz: Fizz, buzz: Buzz): this("${fizz.word}${buzz.word}")

    constructor(fizz: Fizz): this(fizz.word)

    constructor(buzz: Buzz): this(buzz.word)

    constructor(value: Long): this(value.toString())

    init {
        require(word.isNotEmpty()) {
            "FizzBuzz word cannot be empty"
        }
    }

    override fun toString(): String {
        return word
    }
}