package com.example.nutritive_app.controller

import com.example.nutritive_app.entity.Country
import com.example.nutritive_app.repository.CountryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/countries")
class CountryController {

    @Autowired
    lateinit var countryRepository: CountryRepository

    @PostMapping
    fun createCountry(@RequestBody country: Country): Country {
        return countryRepository.save(country)
    }

    @PutMapping("/{id}")
    fun updateCountry(@PathVariable id: Long, @RequestBody updated: Country): ResponseEntity<Country> {
        return countryRepository.findById(id).map { existing ->
            existing.name = updated.name
            ResponseEntity.ok(countryRepository.save(existing))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteCountry(@PathVariable id: Long): ResponseEntity<Void> {
        return countryRepository.findById(id).map { country ->
            println("Country found $country and deleted")
            countryRepository.delete(country)
            ResponseEntity<Void>(HttpStatus.NO_CONTENT)
        }.orElse(ResponseEntity.notFound().build())
    }
}