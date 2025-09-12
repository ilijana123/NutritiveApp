package com.example.nutritive_app.controller

import com.example.nutritive_app.dto.AllergenDTO
import com.example.nutritive_app.entity.Allergen
import com.example.nutritive_app.mapper.toDto
import com.example.nutritive_app.service.AllergenService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/allergens")
class AllergenController(private val allergenService: AllergenService) {

    @GetMapping
    fun getAllAllergens(): List<AllergenDTO> =
        allergenService.getAllAllergens().map { it.toDto() }

    @GetMapping("/{id}")
    fun getAllergensById(@PathVariable("id") allergenId: Long): Allergen =
        allergenService.getAllergensById(allergenId)

    @PostMapping
    fun createAllergen(@RequestBody payload: Allergen): Allergen = allergenService.createAllergen(payload)

    @PutMapping("/{id}")
    fun updateAllergenById(@PathVariable("id") allergenId: Long, @RequestBody payload: Allergen): Allergen =
        allergenService.updateAllergenById(allergenId, payload)


    @DeleteMapping("/{id}")
    fun deleteAllergensById(@PathVariable("id") allergenId: Long): Unit =
        allergenService.deleteAllergensById(allergenId)
}