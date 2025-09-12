package com.example.nutritive_app.dto

import com.example.nutritive_app.entity.Nutriment

data class NutrimentDTO(
    val carbohydrates: Float? = null,
    val carbohydrates_100g: Float? = null,
    val energy: Float? = null,
    val energy_kj_100g: Float? = null,
    val fat: Float? = null,
    val fat_100g: Float? = null,
    val saturated_fat: Float? = null,
    val saturated_fat_100g: Float? = null,
    val sugars: Float? = null,
    val sugars_100g: Float? = null,
    val proteins: Float? = null,
    val proteins_100g: Float? = null,
    val salt: Float? = null,
    val salt_100g: Float? = null,
    val sodium: Float? = null,
    val sodium_100g: Float? = null,
    val fiber: Float? = null,
    val fiber_100g: Float? = null,
) {
    companion object {
        fun fromEntity(entity: Nutriment): NutrimentDTO =
            NutrimentDTO(
                carbohydrates = entity.carbohydrates,
                carbohydrates_100g = entity.carbohydrates_100g,
                energy = entity.energy,
                energy_kj_100g = entity.energy_kj_100g,
                fat = entity.fat,
                fat_100g = entity.fat_100g,
                saturated_fat = entity.saturated_fat,
                saturated_fat_100g = entity.saturated_fat_100g,
                sugars = entity.sugars,
                sugars_100g = entity.sugars_100g,
                proteins = entity.proteins,
                proteins_100g = entity.proteins_100g,
                salt = entity.salt,
                salt_100g = entity.salt_100g,
                sodium = entity.sodium,
                sodium_100g = entity.sodium_100g,
                fiber = entity.fiber,
                fiber_100g = entity.fiber_100g
            )
    }
}
