package com.codely.demo.cat

import com.codely.demo.shared.AgeCalculator
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class CatTest {
    private val id = "92efe4c8-fab9-4cb0-82d9-5c75eeca2dc1"
    private val name = "Trina"
    private val origin = "Vet Shelter"
    private val toy = "Feathers"
    private val birthDate = "2019-01-01"
    private val color = "orange"
    private val fixedDate = LocalDate.of(2021, 8, 31)
    private val breed = "Abyssinian"

    @Test
    fun `should create a vaccinated cat`() {
        val actualCat = Cat.from(
            id = Cat.Id.from(id),
            name = Cat.Name.from(name),
            origin = Cat.Origin.from(origin),
            toy = Cat.Toy.from(toy),
            birthDate = Cat.BirthDate.from(birthDate),
            color = Cat.Color.from(color),
            vaccinated = Cat.Vaccinated.from("true"),
            createdAt = fixedDate,
            breed = Cat.Breed(breed),
        )
        val expectedCat = Cat(
            id = Cat.Id.from(id),
            name = Cat.Name.from(name),
            origin = Cat.Origin.from(origin),
            toy = Cat.Toy.from(toy),
            birthDate = Cat.BirthDate.from(birthDate),
            color = Cat.Color.valueOf(color.uppercase()),
            vaccinated = Cat.Vaccinated.from("true"),
            age = AgeCalculator.calculate(LocalDate.parse(birthDate), fixedDate).years,
            createdAt = fixedDate,
            breed = Cat.Breed(breed),
        )

        assertEquals(expectedCat, actualCat)
    }

    @Test
    fun `should create a no vaccinated cat`() {
        val actualCat = Cat.from(
            id = Cat.Id.from(id),
            name = Cat.Name.from(name),
            origin = Cat.Origin.from(origin),
            toy = Cat.Toy.from(toy),
            birthDate = Cat.BirthDate.from(birthDate),
            color = Cat.Color.from(color),
            vaccinated = Cat.Vaccinated.from("false"),
            createdAt = fixedDate,
            breed = Cat.Breed(breed),
        )
        val expectedCat = Cat(
            id = Cat.Id.from(id),
            name = Cat.Name.from(name),
            origin = Cat.Origin.from(origin),
            toy = Cat.Toy.from(toy),
            birthDate = Cat.BirthDate.from(birthDate),
            color = Cat.Color.valueOf(color.uppercase()),
            vaccinated = Cat.Vaccinated.from("false"),
            age = AgeCalculator.calculate(LocalDate.parse(birthDate), fixedDate).years,
            createdAt = fixedDate,
            breed = Cat.Breed(breed),
        )

        assertEquals(expectedCat, actualCat)
    }
}
