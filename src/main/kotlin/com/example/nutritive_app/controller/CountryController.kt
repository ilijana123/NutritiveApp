package com.example.nutritive_app.controller

import com.example.nutritive_app.entity.Country
import com.example.nutritive_app.service.CountryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/countries")
class CountryController(private val countryService: CountryService) {

    @GetMapping
    fun getAllCountries(): List<Country> = countryService.getAllCountries()

    @GetMapping("/countries/{id}")
    fun getCountriesById(@PathVariable("id") countryId: Long): Country =
        countryService.getCountriesById(countryId)

    @PostMapping
    fun createCountry(@RequestBody payload: Country): Country = countryService.createCountry(payload)

    @PutMapping("/countries/{id}")
    fun updateCountryById(@PathVariable("id") countryId: Long, @RequestBody payload: Country): Country =
        countryService.updateCountryById(countryId, payload)


    @DeleteMapping("/countries/{id}")
    fun deleteCountriesById(@PathVariable("id") countryId: Long): Unit =
        countryService.deleteCountriesById(countryId)
}