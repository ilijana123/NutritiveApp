package com.example.nutritive_app.service

import com.example.nutritive_app.entity.Nutriment
import com.example.nutritive_app.dto.NutrimentDTO
import com.example.nutritive_app.exception.NutrimentNotFoundException
import com.example.nutritive_app.repository.NutrimentRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class NutrimentService(
    private val nutrimentRepository: NutrimentRepository
) {
    fun getAllNutriments(): List<Nutriment> = nutrimentRepository.findAll()

    fun getNutrimentsById(id: Long): Nutriment =
        nutrimentRepository.findById(id).orElseThrow {
            NoSuchElementException("Nutriment with id $id not found")
        }

    fun createNutriment(nutriment: Nutriment): Nutriment = nutrimentRepository.save(nutriment)

    fun updateNutrimentsById(id: Long, updated: Nutriment): Nutriment {
        val existing = getNutrimentsById(id)
        val toSave = existing.copy(
            carbohydrates = updated.carbohydrates,
            carbohydrates_100g = updated.carbohydrates_100g,
            energy = updated.energy,
            energy_kj_100g = updated.energy_kj_100g,
            fat = updated.fat,
            fat_100g = updated.fat_100g,
            fiber = updated.fiber,
            fiber_100g = updated.fiber_100g,
            proteins = updated.proteins,
            proteins_100g = updated.proteins_100g,
            salt = updated.salt,
            salt_100g = updated.salt_100g,
            saturated_fat = updated.saturated_fat,
            saturated_fat_100g = updated.saturated_fat_100g,
            sodium = updated.sodium,
            sodium_100g = updated.sodium_100g,
            sugars = updated.sugars,
            sugars_100g = updated.sugars_100g
        )
        return nutrimentRepository.save(toSave)
    }

    fun deleteNutrimentsById(nutrimentId: Long) {
        return if (nutrimentRepository.existsById(nutrimentId)) {
            nutrimentRepository.deleteById(nutrimentId)
        } else throw NutrimentNotFoundException(HttpStatus.NOT_FOUND, "No matching employee was found")
    }

    fun findOrCreate(nutrimentsDTO: NutrimentDTO?): Nutriment? {
        if (nutrimentsDTO == null) return null
        val nutriment = Nutriment(
            carbohydrates = nutrimentsDTO.carbohydrates,
            carbohydrates_100g = nutrimentsDTO.carbohydrates_100g,
            energy = nutrimentsDTO.energy,
            energy_kj_100g = nutrimentsDTO.energy_kj_100g,
            fat = nutrimentsDTO.fat,
            fat_100g = nutrimentsDTO.fat_100g,
            fiber = nutrimentsDTO .fiber,
            fiber_100g = nutrimentsDTO.fiber_100g,
            proteins = nutrimentsDTO.proteins,
            proteins_100g = nutrimentsDTO.proteins_100g,
            salt = nutrimentsDTO.salt,
            salt_100g = nutrimentsDTO.salt_100g,
            saturated_fat = nutrimentsDTO.saturated_fat,
            saturated_fat_100g = nutrimentsDTO.saturated_fat_100g,
            sodium = nutrimentsDTO.sodium,
            sodium_100g = nutrimentsDTO.sodium_100g,
            sugars = nutrimentsDTO.sugars,
            sugars_100g = nutrimentsDTO.sugars_100g
        )
        return nutrimentRepository.save(nutriment)
    }
}