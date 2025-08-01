package com.example.nutritive_app.service

import com.example.nutritive_app.entity.Country
import com.example.nutritive_app.repository.CountryRepository
import org.springframework.stereotype.Service

@Service
class CountryService(private val countryRepository: CountryRepository) {

    fun findOrCreate(name: String): Country {
        return countryRepository.findByName(name) ?: countryRepository.save(Country(name = name))
    }

    fun findOrCreateAll(names: List<String>?): MutableSet<Country> {
        return names.orEmpty()
            .filter { it.startsWith("en:")}
            .map { findOrCreate(it.removePrefix("en:")
            .trim()
            .split('-')
            .joinToString(" ") { word -> word.replaceFirstChar { it.uppercaseChar() } })}
            .toMutableSet()
    }
}
