package com.codely.demo.cat

import com.codely.demo.shared.AgeCalculator
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.*

data class Cat( // DataClasses
    val id: Id,
    val name: Name,
    val origin: Origin,
    val toy: Toy,
    val vaccinated: Vaccinated,
    val birthDate: BirthDate,
    val color: Color,
    val age: Int,
    val breed: Breed,
    val createdAt: LocalDate,
) {
    enum class Color {
        BLACK, RED, CINNAMON, BLUE, CREAM, LILAC, FAWN, WHITE, ORANGE;
        companion object {
            fun from(value: String?) = if (value.isNullOrBlank() || value.isNullOrEmpty() || !isValid(value)) {
                throw InvalidColor(value)
            } else {
                valueOf(value.uppercase())
            }

            private fun isValid(value: String): Boolean = values().map { it.name }.contains(value.uppercase())
        }
    }
    companion object {
        fun from(
            id: Id,
            name: Name,
            origin: Origin,
            toy: Toy,
            birthDate: BirthDate,
            color: Color,
            vaccinated: Vaccinated,
            breed: Breed,
            createdAt: LocalDate,
        ) = Cat(
            id = id,
            name = name,
            origin = origin,
            toy = toy,
            vaccinated = vaccinated,
            birthDate = birthDate,
            color = color,
            age = AgeCalculator.calculate(birthDate.value, createdAt).years,
            breed = breed,
            createdAt = createdAt,
        )
    }
    data class Name(val value: String) {
        companion object {
            fun from(value: String?) = if (value.isNullOrBlank() || value.isNullOrEmpty()) {
                throw InvalidName(value)
            } else {
                Name(value)
            }
        }
    }
    data class Id(val value: UUID) {
        companion object {
            fun from(value: String?) = try {
                Id(UUID.fromString(value))
            } catch (exception: Throwable) {
                throw InvalidId(value)
            }
        }
    }

    data class Origin(val value: String) {
        companion object {
            fun from(value: String?) = if (value.isNullOrEmpty() || value.isNullOrBlank()) {
                throw InvalidOrigin(value)
            } else {
                Origin(value)
            }
        }
    }

    data class Toy(val value: String) {
        companion object {
            fun from(value: String?) = if (value.isNullOrBlank() || value.isNullOrEmpty()) {
                throw InvalidToy(value)
            } else {
                Toy(value)
            }
        }
    }

    data class Vaccinated(val value: Boolean) {
        companion object {
            fun from(value: String?) = if (value.isNullOrBlank() || value.isNullOrEmpty() || !isValid(value)) {
                throw InvalidVaccinated(value)
            } else {
                Vaccinated(value.toBoolean())
            }

            private fun isValid(value: String) = listOf("true", "false").contains(value.lowercase())
        }
    }

    data class BirthDate(val value: LocalDate) {
        companion object {
            fun from(value: String?) = if (value.isNullOrBlank() || value.isNullOrEmpty()) {
                throw InvalidBirthDate(value)
            } else {
                try {
                    BirthDate(LocalDate.parse(value))
                } catch (e: DateTimeParseException) {
                    throw InvalidBirthDate(value)
                }
            }
        }
    }

    data class Breed(val value: String)
}
