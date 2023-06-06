package com.codely.demo

import java.time.LocalDate

fun main() {
    println("Please enter a date with format <yyyy-mm-dd>")
    val readLine: String? = null
    readLine?.let {
        val input = LocalDate.parse(readLine)
        println("You wrote $input ${input.dayOfWeek}")
    }
}
