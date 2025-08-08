package com.example.nutritive_app.service

import com.example.nutritive_app.entity.Allergen
import com.example.nutritive_app.exception.AllergenNotFoundException
import com.example.nutritive_app.repository.AllergenRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class AllergenService(private val allergenRepository: AllergenRepository) {

    fun getAllAllergens(): List<Allergen> = allergenRepository.findAll()

    fun getAllergensById(allergenId: Long): Allergen = allergenRepository.findById(allergenId)
        .orElseThrow { AllergenNotFoundException(HttpStatus.NOT_FOUND, "No matching allergen was found") }

    fun createAllergen(allergen: Allergen): Allergen = allergenRepository.save(allergen)

    fun updateAllergenById(allergenId: Long, allergen: Allergen): Allergen {
        return if (allergenRepository.existsById(allergenId)) {
            allergenRepository.save(
                Allergen(
                    id = allergen.id,
                    name = allergen.name,
                    products = allergen.products,
                )
            )
        } else throw AllergenNotFoundException(HttpStatus.NOT_FOUND, "No matching allergen was found")
    }

    fun deleteAllergensById(allergenId: Long) {
        return if (allergenRepository.existsById(allergenId)) {
            allergenRepository.deleteById(allergenId)
        } else throw AllergenNotFoundException(HttpStatus.NOT_FOUND, "No matching allergen was found")
    }

    fun findOrCreate(name: String): Allergen {
        return allergenRepository.findByName(name) ?: allergenRepository.save(Allergen(name = name))
    }

    fun findOrCreateAll(names: List<String>?): MutableSet<Allergen> {
        if (names == null) return mutableSetOf()
        return names
            .filter { it.startsWith("en:")}
            .map { findOrCreate(it.removePrefix("en:")
                .trim()
                .split('-')
                .joinToString(" ") { word -> word.replaceFirstChar { it.uppercaseChar() } })}
            .toMutableSet()
    }
}
