package com.example.nutritive_app.mapper

import com.example.nutritive_app.dto.AllergenDTO
import com.example.nutritive_app.entity.Allergen

fun Allergen.toDto() = AllergenDTO(
    id = this.id!!,
    name = this.name
)

fun Set<Allergen>.toDtoSet(): Set<AllergenDTO> = this.map { it.toDto() }.toSet()
