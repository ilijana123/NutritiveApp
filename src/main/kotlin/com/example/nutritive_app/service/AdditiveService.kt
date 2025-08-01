package com.example.nutritive_app.service

import com.example.nutritive_app.entity.Additive
import com.example.nutritive_app.repository.AdditiveRepository
import org.springframework.stereotype.Service

@Service
class AdditiveService(private val additiveRepository: AdditiveRepository) {

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
