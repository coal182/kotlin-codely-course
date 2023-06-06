package com.codely.demo

import java.time.LocalDate
import java.time.Period
import kotlin.system.exitProcess

fun main() {
    println("Please enter a date with format <yyyy-mm-dd>")
    supportNullableString(readLine()).takeUnless {
        // returns null if the condition is true /-> takeIf would be in the opposite way
        it.isNullOrEmpty() || it.isNullOrBlank()
    }?.let {
        // like map
        LocalDate.parse(it)
    }.apply {
        // to make mutations
        if (this == null) {
            println("The date is not valid")
            exitProcess(1)
        }
    }?.also {
        // side effect
        println("You wrote $it")
    }?.run {
        // returns the inside block execution result
        calculateDifference()
    }
}

fun supportNullableString(line: String?) = line

// Extension function
private fun LocalDate.calculateDifference() = with(Period.between(this, LocalDate.now())) {
    // to avoid declaring variables
    when {
        // when is similar to a switch or an if-else
        // this keyword is not neccesary (years instead this.years)
        years > 0 -> println("The difference between the date you wrote and today is $years years")
        months > 0 -> println("The difference between the date you wrote and today is $months months")
        days > 0 -> println("The difference between the date you wrote and today is $days days")
    }
}
