package com.example.nutritive_app.controller

import com.example.nutritive_app.dto.AllergenDTO
import com.example.nutritive_app.dto.request.UpdateUserAllergensRequest
import com.example.nutritive_app.entity.Allergen
import com.example.nutritive_app.mapper.toDtoSet
import com.example.nutritive_app.service.UserAllergenService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users/{userId}/allergens")
class UserAllergenController(
    private val service: UserAllergenService
) {

    @GetMapping
    fun list(@PathVariable userId: Long): ResponseEntity<Set<AllergenDTO>> {
        val allergens = service.getUserAllergens(userId)
        return ResponseEntity.ok(allergens.toDtoSet())
    }

    @PutMapping
    fun replace(
        @PathVariable userId: Long,
        @RequestBody body: UpdateUserAllergensRequest
    ): ResponseEntity<Set<Allergen>> =
        ResponseEntity.ok(service.replaceUserAllergens(userId, body.allergenIds))

    @PostMapping("/{allergenId}")
    fun add(
        @PathVariable userId: Long,
        @PathVariable allergenId: Long
    ): ResponseEntity<Set<Allergen>> =
        ResponseEntity.ok(service.addUserAllergen(userId, allergenId))

    @DeleteMapping("/{allergenId}")
    fun remove(
        @PathVariable userId: Long,
        @PathVariable allergenId: Long
    ): ResponseEntity<Set<Allergen>> =
        ResponseEntity.ok(service.removeUserAllergen(userId, allergenId))
}