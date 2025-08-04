package com.example.nutritive_app.service

import com.example.nutritive_app.entity.Additive
import com.example.nutritive_app.exception.AdditiveNotFoundException
import com.example.nutritive_app.repository.AdditiveRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
@Service
class AdditiveService(private val additiveRepository: AdditiveRepository) {

    fun getAllAdditives(): List<Additive> = additiveRepository.findAll()

    fun getAdditivesById(additiveId: Long): Additive = additiveRepository.findById(additiveId)
        .orElseThrow { AdditiveNotFoundException(HttpStatus.NOT_FOUND, "No matching additive was found") }

    fun createAdditive(additive: Additive): Additive = additiveRepository.save(additive)

    fun updateAdditiveById(additiveId: Long, additive: Additive): Additive {
        return if (additiveRepository.existsById(additiveId)) {
            additiveRepository.save(
                Additive(
                    id = additive.id,
                    name = additive.name,
                    products = additive.products,
                )
            )
        } else throw AdditiveNotFoundException(HttpStatus.NOT_FOUND, "No matching additive was found")
    }

    fun deleteAdditivesById(additiveId: Long) {
        return if (additiveRepository.existsById(additiveId)) {
            additiveRepository.deleteById(additiveId)
        } else throw AdditiveNotFoundException(HttpStatus.NOT_FOUND, "No matching additive was found")
    }

    fun findOrCreate(name: String): Additive {
        return additiveRepository.findByName(name) ?: additiveRepository.save(Additive(name = name))
    }

    fun findOrCreateAll(names: List<String>?): MutableSet<Additive> {
        return names.orEmpty()
            .filter { it.startsWith("en:")}
            .map { findOrCreate(it.removePrefix("en:").trim())}
            .toMutableSet()
    }
}
