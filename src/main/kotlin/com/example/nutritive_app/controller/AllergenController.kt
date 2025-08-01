package com.example.nutritive_app.controller

import com.example.nutritive_app.entity.Allergen
import com.example.nutritive_app.repository.AllergenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/allergens")
class AllergenController {

    @Autowired
    lateinit var allergenRepository: AllergenRepository

    @PostMapping
    fun createAllergen(@RequestBody allergen: Allergen): Allergen {
        return allergenRepository.save(allergen)
    }

    @PutMapping("/{id}")
    fun updateAllergen(@PathVariable id: Long, @RequestBody updated: Allergen): ResponseEntity<Allergen> {
        return allergenRepository.findById(id).map { existing ->
            val updatedAllergen = existing.copy(name = updated.name)
            ResponseEntity.ok(allergenRepository.save(updatedAllergen))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteAllergen(@PathVariable id: Long) {
        allergenRepository.findById(id).map { allergen ->
            print("Allergen found $allergen and deleted")
            allergenRepository.delete(allergen)
            ResponseEntity<Void>(HttpStatus.NO_CONTENT)
        }.orElse(ResponseEntity.notFound().build())
    }
}