package com.codely.demo.cat

import com.codely.demo.shared.Clock
import com.codely.demo.shared.Reader
import com.codely.demo.shared.Writer
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import kotlin.test.assertEquals

class CatCreatorTest {
    private val id = "92efe4c8-fab9-4cb0-82d9-5c75eeca2dc1"
    private val name = "Trina"
    private val origin = "Vet Shelter"
    private val toy = "Feathers"
    private val vaccinated = "true"
    private val notVaccinated = "false"
    private val birthDate = "2019-01-01"
    private val color = "orange"
    private val fixedDate = LocalDate.of(2021, 8, 31)

    @Test
    fun `should create a vaccinated cat`() {
        val reader = mockk<Reader>()
        val writer = mockk<Writer>(relaxed = true)
        val clock = mockk<Clock>()
        val repository = InMemoryCatRepository()
        every { clock.now() } returns fixedDate
        every { reader.read() } returns id andThen name andThen origin andThen toy andThen vaccinated andThen color andThen birthDate

        val creator = CatCreator(reader, writer, clock, repository)
        creator.create()

        val expectedCat = Cat.from(
            id = Cat.Id.from(id),
            name = Cat.Name.from(name),
            origin = Cat.Origin.from(origin),
            toy = Cat.Toy.from(toy),
            birthDate = Cat.BirthDate.from(birthDate),
            color = Cat.Color.from(color),
            vaccinated = Cat.Vaccinated.from("true"),
            createdAt = fixedDate,
        )

        assertEquals(
            mapOf(
                expectedCat.id to expectedCat,
            ),
            repository.findAll(),
        )
    }

    @Test
    fun `should create a not vaccinated cat`() {
        val reader = mockk<Reader>()
        val writer = mockk<Writer>(relaxed = true)
        val clock = mockk<Clock>()
        val repository = InMemoryCatRepository()
        every { clock.now() } returns fixedDate
        every { reader.read() } returns id andThen name andThen origin andThen toy andThen notVaccinated andThen color andThen birthDate

        val creator = CatCreator(reader, writer, clock, repository)
        creator.create()

        val expectedCat = Cat.from(
            id = Cat.Id.from(id),
            name = Cat.Name.from(name),
            origin = Cat.Origin.from(origin),
            toy = Cat.Toy.from(toy),
            color = Cat.Color.from(color),
            birthDate = Cat.BirthDate.from(birthDate),
            vaccinated = Cat.Vaccinated.from("false"),
            createdAt = fixedDate,
        )

        assertEquals(
            mapOf(
                expectedCat.id to expectedCat,
            ),
            repository.findAll(),
        )
    }

    @Test
    fun `should fail creating a cat without name`() {
        val reader = mockk<Reader>()
        val writer = mockk<Writer>(relaxed = true)
        val clock = mockk<Clock>()
        val repository = InMemoryCatRepository()

        every { clock.now() } returns fixedDate
        every { reader.read() } returns id andThen "" andThen origin andThen toy andThen vaccinated andThen birthDate andThen color

        val creator = CatCreator(reader, writer, clock, repository)
        assertThrows<InvalidName> { creator.create() }
    }

    @Test
    fun `should fail creating a cat with empty name`() {
        val reader = mockk<Reader>()
        val writer = mockk<Writer>(relaxed = true)
        val clock = mockk<Clock>()
        val repository = InMemoryCatRepository()

        every { clock.now() } returns fixedDate
        every { reader.read() } returns id andThen "  " andThen origin andThen toy andThen vaccinated andThen birthDate andThen color

        val creator = CatCreator(reader, writer, clock, repository)
        assertThrows<InvalidName> { creator.create() }
    }

    @Test
    fun `should fail creating a cat without origin`() {
        val reader = mockk<Reader>()
        val writer = mockk<Writer>(relaxed = true)
        val clock = mockk<Clock>()
        val repository = InMemoryCatRepository()

        every { clock.now() } returns fixedDate
        every { reader.read() } returns id andThen name andThen "" andThen toy andThen vaccinated andThen birthDate andThen color

        val creator = CatCreator(reader, writer, clock, repository)
        assertThrows<InvalidOrigin> { creator.create() }
    }

    @Test
    fun `should fail creating a cat with empty origin`() {
        val reader = mockk<Reader>()
        val writer = mockk<Writer>(relaxed = true)
        val clock = mockk<Clock>()
        val repository = InMemoryCatRepository()

        every { clock.now() } returns fixedDate
        every { reader.read() } returns id andThen name andThen "  " andThen toy andThen vaccinated andThen birthDate andThen color

        val creator = CatCreator(reader, writer, clock, repository)
        assertThrows<InvalidOrigin> { creator.create() }
    }

    @Test
    fun `should fail creating a cat without color`() {
        val reader = mockk<Reader>()
        val writer = mockk<Writer>(relaxed = true)
        val clock = mockk<Clock>()
        val repository = InMemoryCatRepository()
        every { clock.now() } returns fixedDate
        every { reader.read() } returns id andThen name andThen origin andThen toy andThen vaccinated andThen "" andThen birthDate

        val creator = CatCreator(reader, writer, clock, repository)
        assertThrows<InvalidColor> { creator.create() }
    }

    @Test
    fun `should fail creating a cat with empty color`() {
        val reader = mockk<Reader>()
        val writer = mockk<Writer>(relaxed = true)
        val clock = mockk<Clock>()
        val repository = InMemoryCatRepository()
        every { clock.now() } returns fixedDate
        every { reader.read() } returns id andThen name andThen origin andThen toy andThen vaccinated andThen " " andThen birthDate

        val creator = CatCreator(reader, writer, clock, repository)
        assertThrows<InvalidColor> { creator.create() }
    }

    @Test
    fun `should fail creating a cat with a non uuid`() {
        val reader = mockk<Reader>()
        val writer = mockk<Writer>(relaxed = true)
        val clock = mockk<Clock>()
        val repository = InMemoryCatRepository()
        every { clock.now() } returns fixedDate
        every { reader.read() } returns "1" andThen name andThen origin andThen toy andThen vaccinated andThen color andThen birthDate

        val creator = CatCreator(reader, writer, clock, repository)
        assertThrows<InvalidId> { creator.create() }
    }

    @Test
    fun `should fail creating a cat with invalid vaccinated value`() {
        val reader = mockk<Reader>()
        val writer = mockk<Writer>(relaxed = true)
        val clock = mockk<Clock>()
        val repository = InMemoryCatRepository()
        every { clock.now() } returns fixedDate
        every { reader.read() } returns id andThen name andThen origin andThen toy andThen "maybe" andThen color andThen birthDate

        val creator = CatCreator(reader, writer, clock, repository)
        assertThrows<InvalidVaccinated> { creator.create() }
    }

    @Test
    fun `should fail creating a cat with invalid birth date value`() {
        val reader = mockk<Reader>()
        val writer = mockk<Writer>(relaxed = true)
        val clock = mockk<Clock>()
        val repository = InMemoryCatRepository()
        every { clock.now() } returns fixedDate
        every { reader.read() } returns id andThen name andThen origin andThen toy andThen vaccinated andThen color andThen "today"

        val creator = CatCreator(reader, writer, clock, repository)
        assertThrows<InvalidBirthDate> { creator.create() }
    }
}
