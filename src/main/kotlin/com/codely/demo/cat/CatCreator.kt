package com.codely.demo.cat

import com.codely.demo.shared.Clock
import com.codely.demo.shared.Reader
import com.codely.demo.shared.Writer
import java.time.LocalDate
import java.util.*

class CatCreator(val reader: Reader, val writer: Writer, val clock: Clock, val repository: CatRepository) {
    fun create(): Cat {
        val id = obtainInput("Please enter an id for your cat")
        val name = Name.from(obtainInput("Please enter the name of your cat"))
        val origin = obtainInput("Please enter where your cat came from")
        val toy = obtainInput("What is your cat's favourite toy?")
        val vaccinated = obtainInput("Is your cat vaccinated?")
        val color = obtainInput("What color your cat is?")
        val birthDate = obtainInput("When did your cat birth?")

        if (origin.isNullOrBlank() || origin.isNullOrEmpty()) {
            throw InvalidOrigin(origin)
        }

        if (toy.isNullOrBlank() || toy.isNullOrEmpty()) {
            throw InvalidToy(toy)
        }

        if (color.isNullOrBlank() || color.isNullOrEmpty()) {
            throw InvalidColor(color)
        }

        if (vaccinated.toBoolean()) {
            val cat = Cat.vaccinatedWith(
                id = UUID.fromString(id),
                name = name.value,
                origin = origin,
                toy = toy,
                birthDate = LocalDate.parse(birthDate),
                color = color,
                createdAt = clock.now(),
            )
            repository.save(cat)
            return cat
        } else {
            val cat = Cat.notVaccinatedWith(
                id = UUID.fromString(id),
                name = name.value,
                origin = origin,
                toy = toy,
                birthDate = LocalDate.parse(birthDate),
                color = color,
                createdAt = clock.now(),
            )
            repository.save(cat)
            return cat
        }
    }

    private fun obtainInput(message: String) = writer.write(message).run { reader.read() }
}
