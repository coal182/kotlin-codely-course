package com.codely.demo.app

import com.codely.demo.shared.Clock
import com.codely.demo.shared.Reader
import com.codely.demo.shared.Writer
import java.time.LocalDate
import java.time.Period
import kotlin.system.exitProcess

open class AppClock(private val reader: Reader, private val writer: Writer, private val clock: Clock) {
    fun execute() {
        writer.write("Please enter a date with format <yyyy-MM-dd>")
        val line = reader.read()
        line.takeUnless {
            // returns null if the condition is true /-> takeIf would be in the opposite way
            !it.isNullOrEmpty() && !it.isNullOrBlank()
        }?.let {
            // like map
            writer.write("The introduced date <$it> is not valid")
            return
        }
        line.takeUnless {
            it.isNullOrEmpty()
        }?.let {
            LocalDate.parse(it)
        }.apply {
            // to make mutations
            if (this == null) {
                writer.write("The introduced date <$this> is not valid")
                exitProcess(1)
            }
        }.also {
            // side effect
            writer.write("You wrote the date $it")
        }?.run {
            // returns the inside block execution result
            calculateDifferenceUntilToday()
        }

        writer.write("Bye!")
    }

    // Extension function
    private fun LocalDate.calculateDifferenceUntilToday() = with(Period.between(this, clock.now())) {
        // to avoid declaring variables
        when {
            // when is similar to a switch or an if-else
            // this keyword is not neccesary (years instead this.years)
            // years >= 18 -> println("The difference between the date you wrote and today is $years years (>= 18 years)")
            years > 0 -> writer.write("The difference between the date you wrote and today is $years years")
            months > 0 -> writer.write("The difference between the date you wrote and today is $months months")
            days > 0 -> writer.write("The difference between the date you wrote and today is $days days")
        }
    }
}
