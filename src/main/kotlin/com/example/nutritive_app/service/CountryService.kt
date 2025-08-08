package com.example.nutritive_app.service

import com.example.nutritive_app.entity.Country
import com.example.nutritive_app.exception.CountryNotFoundException
import com.example.nutritive_app.repository.CountryRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class CountryService(private val countryRepository: CountryRepository) {

    fun getAllCountries(): List<Country> = countryRepository.findAll()

    fun getCountriesById(countryId: Long): Country = countryRepository.findById(countryId)
        .orElseThrow { CountryNotFoundException(HttpStatus.NOT_FOUND, "No matching country was found") }

    fun createCountry(country: Country): Country = countryRepository.save(country)

    fun updateCountryById(countryId: Long, country: Country): Country {
        return if (countryRepository.existsById(countryId)) {
            countryRepository.save(
                Country(
                    id = country.id,
                    name = country.name,
                    products = country.products,
                )
            )
        } else throw CountryNotFoundException(HttpStatus.NOT_FOUND, "No matching country was found")
    }

    fun deleteCountriesById(countryId: Long) {
        return if (countryRepository.existsById(countryId)) {
            countryRepository.deleteById(countryId)
        } else throw CountryNotFoundException(HttpStatus.NOT_FOUND, "No matching country was found")
    }

    fun findOrCreate(name: String): Country {
        return countryRepository.findByName(name) ?: countryRepository.save(Country(name = name))
    }

    fun findOrCreateAll(names: List<String>?): MutableSet<Country> {
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
