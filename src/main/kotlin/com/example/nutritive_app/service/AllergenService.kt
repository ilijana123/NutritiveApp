package com.example.nutritive_app.service

import com.example.nutritive_app.entity.Allergen
import com.example.nutritive_app.repository.AllergenRepository
import org.springframework.stereotype.Service

@Service
class AllergenService(private val allergenRepository: AllergenRepository) {

    fun findOrCreate(name: String): Allergen {
        return allergenRepository.findByName(name) ?: allergenRepository.save(Allergen(name = name))
    }

    fun findOrCreateAll(names: List<String>?): MutableSet<Allergen> {
        return names.orEmpty()
            .filter { it.startsWith("en:")}
            .map { findOrCreate(it.removePrefix("en:")
            .trim()
            .split('-')
            .joinToString(" ") { word -> word.replaceFirstChar { it.uppercaseChar() } })}
            .toMutableSet()
    }
}
