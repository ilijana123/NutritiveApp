package com.example.nutritive_app.service

import com.example.nutritive_app.entity.Category
import com.example.nutritive_app.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun findOrCreate(name: String): Category {
        return categoryRepository.findByName(name) ?: categoryRepository.save(Category(name = name))
    }

    fun findOrCreateAll(names: List<String>?): MutableSet<Category> {
        return names.orEmpty()
            .filter { it.startsWith("en:")}
            .map { findOrCreate(it.removePrefix("en:")
            .trim()
            .split('-')
            .joinToString(" ") { word -> word.replaceFirstChar { it.uppercaseChar() } })}
            .toMutableSet()
    }
}
