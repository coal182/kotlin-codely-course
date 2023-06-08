package com.codely.demo.cat

import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.*
import kotlin.test.assertEquals

class CatTest {
    private val id = "92efe4c8-fab9-4cb0-82d9-5c75eeca2dc1"
    private val name = "Trina"
    private val origin = "Vet Shelter"
    private val toy = "Feathers"
    private val birthDate = "2019-01-01"
    private val color = "orange"
    private val fixedDate = LocalDate.of(2021, 8, 31)

    @Test
    fun `should create a vaccinated cat`() {
        val actualCat = Cat.vaccinatedWith(
            id = UUID.fromString(id),
            name = name,
            origin = origin,
            toy = toy,
            birthDate = LocalDate.parse(birthDate),
            color = color,
            createdAt = fixedDate,
        )
        val expectedCat = Cat(
            id = UUID.fromString(id),
            name = name,
            origin = origin,
            toy = toy,
            vaccinated = true,
            birthDate = LocalDate.parse(birthDate),
            color = Cat.Color.valueOf(color.uppercase()),
            createdAt = fixedDate,
        )

        assertEquals(expectedCat, actualCat)
    }

    @Test
    fun `should create a no vaccinated cat`() {
        val actualCat = Cat.notVaccinatedWith(
            id = UUID.fromString(id),
            name = name,
            origin = origin,
            toy = toy,
            birthDate = LocalDate.parse(birthDate),
            color = color,
            createdAt = fixedDate,
        )
        val expectedCat = Cat(
            id = UUID.fromString(id),
            name = name,
            origin = origin,
            toy = toy,
            vaccinated = false,
            birthDate = LocalDate.parse(birthDate),
            color = Cat.Color.valueOf(color.uppercase()),
            createdAt = fixedDate,
        )

        assertEquals(expectedCat, actualCat)
    }
}
