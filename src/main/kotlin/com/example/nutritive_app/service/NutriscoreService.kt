package com.example.nutritive_app.service

import com.example.nutritive_app.dto.NutriscoreDTO
import com.example.nutritive_app.entity.Nutriscore
import com.example.nutritive_app.repository.NutriscoreRepository
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class NutriscoreService(
    private val nutriscoreRepository: NutriscoreRepository
) {

    fun getAll(): List<Nutriscore> = nutriscoreRepository.findAll()

    fun getById(id: Long): Nutriscore =
        nutriscoreRepository.findById(id).orElseThrow {
            NoSuchElementException("Nutriscore with id $id not found")
        }

    fun create(nutriscore: Nutriscore): Nutriscore = nutriscoreRepository.save(nutriscore)

    fun update(id: Long, updated: Nutriscore): Nutriscore {
        val existing = getById(id)
        val toSave = existing.copy(
            grade = updated.grade,
            score = updated.score,
            positivePoints = updated.positivePoints,
            negativePoints = updated.negativePoints
        )
        return nutriscoreRepository.save(toSave)
    }

    fun delete(id: Long) = nutriscoreRepository.deleteById(id)
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
