package com.example.nutritive_app.dto

import com.example.nutritive_app.entity.Nutriscore

data class NutriscoreDTO(
    val grade: String?,
    val score: Int?,
    val positivePoints: Int?,
    val negativePoints: Int?
) {
    companion object {
        fun fromEntity(entity: Nutriscore): NutriscoreDTO =
            NutriscoreDTO(
                grade = entity.grade,
                score = entity.score,
                positivePoints = entity.positivePoints,
                negativePoints = entity.negativePoints
            )
    }
}
