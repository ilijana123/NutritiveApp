package com.example.nutritive_app.service

import com.example.nutritive_app.entity.Tag
import com.example.nutritive_app.repository.TagRepository
import org.springframework.stereotype.Service

@Service
class TagService(private val tagRepository: TagRepository) {

    fun findOrCreate(name: String): Tag {
        return tagRepository.findByName(name) ?: tagRepository.save(Tag(name = name))
    }

    fun findOrCreateAll(names: List<String>?): MutableSet<Tag> {
        return names.orEmpty()
            .filter { it.startsWith("en:")}
            .map { findOrCreate(it.removePrefix("en:")
            .trim()
            .split('-')
            .joinToString(" ") { word -> word.replaceFirstChar { it.uppercaseChar() } })}
            .toMutableSet()
    }
}
