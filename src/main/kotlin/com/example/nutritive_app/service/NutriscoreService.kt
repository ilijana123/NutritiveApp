package com.example.nutritive_app.service

import com.example.nutritive_app.dto.NutriscoreDTO
import com.example.nutritive_app.entity.Nutriscore
import com.example.nutritive_app.repository.NutriscoreRepository
import org.json.JSONObject
import org.springframework.stereotype.Service
import org.springframework.http.HttpStatus
import com.example.nutritive_app.exception.NutriscoreNotFoundException

@Service
class NutriscoreService(
    private val nutriscoreRepository: NutriscoreRepository
) {

    fun getAllNutriscores(): List<Nutriscore> = nutriscoreRepository.findAll()

    fun getNutriscoresById(id: Long): Nutriscore =
        nutriscoreRepository.findById(id).orElseThrow {
            NoSuchElementException("Nutriscore with id $id not found")
        }

    fun createNutriscore(nutriscore: Nutriscore): Nutriscore = nutriscoreRepository.save(nutriscore)

    fun updateNutriscoreById(id: Long, updated: Nutriscore): Nutriscore {
        val existing = getNutriscoresById(id)
        val toSave = existing.copy(
            grade = updated.grade,
            score = updated.score,
            positivePoints = updated.positivePoints,
            negativePoints = updated.negativePoints
        )
        return nutriscoreRepository.save(toSave)
    }

    fun deleteNutriscoresById(nutriscoreId: Long) {
        return if(nutriscoreRepository.existsById(nutriscoreId)) {
            nutriscoreRepository.deleteById(nutriscoreId)
        } else throw NutriscoreNotFoundException(HttpStatus.NOT_FOUND, "No matching nutriscore was found")
    }

    fun findOrCreate(dto: NutriscoreDTO?): Nutriscore {
        val nutriscore = Nutriscore(
            grade = dto?.grade,
            score = dto?.score,
            positivePoints = dto?.positivePoints,
            negativePoints = dto?.negativePoints
        )
        return nutriscoreRepository.save(nutriscore)
    }

    fun extractAndSaveFromJson(json: JSONObject?): Nutriscore? {
        if (json == null) return null

        val latestYear = json.keys().asSequence()
            .mapNotNull { it.toString().toIntOrNull() }
            .maxOrNull() ?: return null

        val yearData = json.getJSONObject(latestYear.toString())
        val data = yearData.optJSONObject("data") ?: return null

        val nutriscore = Nutriscore(
            grade = yearData.optString("grade"),
            score = yearData.optInt("score"),
            positivePoints = data.optInt("positive_points"),
            negativePoints = data.optInt("negative_points")
        )

        return nutriscoreRepository.save(nutriscore)
    }
}
