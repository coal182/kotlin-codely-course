package com.codely.demo.cat

import com.codely.demo.shared.Clock
import com.codely.demo.shared.Reader
import com.codely.demo.shared.Writer
import java.time.LocalDate
import java.util.*

class CatCreator(val reader: Reader, val writer: Writer, val clock: Clock, val repository: CatRepository) {
    fun create(): Cat {
        writer.write("Please enter an id for your cat")
        val id = reader.read()
        writer.write("Please enter the name of your cat")
        val name = reader.read()
        writer.write("Please enter where your cat came from")
        val origin = reader.read()
        writer.write("What is your cat's favourite toy?")
        val toy = reader.read()
        writer.write("Is your cat vaccinated?")
        val vaccinated = reader.read()
        writer.write("When did your cat birth?")
        val birthDate = reader.read()
        writer.write("What color your cat is?")
        val color = reader.read()

        if (name.isNullOrBlank() || name.isNullOrEmpty() || origin.isNullOrBlank() || origin.isNullOrEmpty() || toy.isNullOrBlank() || toy.isNullOrEmpty() || color.isNullOrBlank() || color.isNullOrEmpty()) {
            throw IllegalArgumentException()
        }

        if (vaccinated.toBoolean()) {
            val cat = Cat.vaccinatedWith(
                id = UUID.fromString(id),
                name = name,
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
                name = name,
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
}
