package com.codely.demo

import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

class AppTestWithMockito {

    @Test
    fun `should calculate the difference and return 31 years`() {
        val reader = mock<Reader>()
        val writer = mock<Writer>()
        val clock = mock<Clock>()

        val app = App(reader, writer, clock)

        whenever(reader.read()).thenReturn("1990-08-31")
        whenever(clock.now()).thenReturn(LocalDate.parse("2021-09-02"))
        doNothing().`when`(writer).write(any())

        app.execute()

        verify(writer).write("The difference between the date you wrote and today is 31 years")
    }

    @Test
    fun `should calculate the difference and return 11 months`() {
        val reader = mock<Reader>()
        val writer = mock<Writer>()
        val clock = mock<Clock>()
        val app = App(reader, writer, clock)
        whenever(reader.read()).thenReturn("2020-09-03")
        whenever(clock.now()).thenReturn(LocalDate.parse("2021-09-02"))

        doNothing().`when`(writer).write(any())

        app.execute()

        verify(writer).write("The difference between the date you wrote and today is 11 months")
    }

    @Test
    fun `should calculate the difference and return 2 days`() {
        val reader = mock<Reader>()
        val writer = mock<Writer>()
        val clock = mock<Clock>()
        val app = App(reader, writer, clock)
        whenever(reader.read()).thenReturn("2021-08-31")
        whenever(clock.now()).thenReturn(LocalDate.parse("2021-09-02"))
        doNothing().`when`(writer).write(any())

        app.execute()

        verify(writer).write("The difference between the date you wrote and today is 2 days")
    }

    @Test
    fun `fail when the introduced date is empty`() {
        val reader = mock<Reader>()
        val writer = mock<Writer>()
        val clock = mock<Clock>()
        val app = App(reader, writer, clock)
        doNothing().`when`(writer).write(any())
        whenever(reader.read()).thenReturn("")

        app.execute()

        verify(writer).write("The introduced date <> is not valid")
    }

    @Test
    fun `fail when the introduced date is blank`() {
        val reader = mock<Reader>()
        val writer = mock<Writer>()
        val clock = mock<Clock>()
        val app = App(reader, writer, clock)
        doNothing().`when`(writer).write(any())
        whenever(reader.read()).thenReturn(" ")

        app.execute()

        verify(writer).write("The introduced date < > is not valid")
    }
}
