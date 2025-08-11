package com.example.nutritive_app.service

import com.example.nutritive_app.entity.Tag
import com.example.nutritive_app.repository.TagRepository
import org.springframework.stereotype.Service
import com.example.nutritive_app.exception.TagNotFoundException
import org.springframework.http.HttpStatus

@Service
class TagService(private val tagRepository: TagRepository) {

    fun getAllTags(): List<Tag> = tagRepository.findAll()

    fun getTagsById(tagId: Long): Tag = tagRepository.findById(tagId)
        .orElseThrow { TagNotFoundException(HttpStatus.NOT_FOUND, "No matching tag was found") }

    fun createTag(tag: Tag): Tag = tagRepository.save(tag)

    fun updateTagById(tagId: Long, tag: Tag): Tag {
        return if (tagRepository.existsById(tagId)) {
            tagRepository.save(
                Tag(
                    id = tagId,
                    name = tag.name,
                    products = tag.products,
                )
            )
        } else throw TagNotFoundException(HttpStatus.NOT_FOUND, "No matching tag was found")
    }

    fun deleteTagsById(tagId: Long) {
        return if (tagRepository.existsById(tagId)) {
            tagRepository.deleteById(tagId)
        } else throw TagNotFoundException(HttpStatus.NOT_FOUND, "No matching tag was found")
    }
    fun findOrCreate(name: String): Tag {
        return tagRepository.findByName(name) ?: tagRepository.save(Tag(name = name))
    }

    fun findOrCreateAll(names: List<String>?): MutableSet<Tag> {
        if (names == null) return mutableSetOf()
        return names
            .filter { it.startsWith("en:")}
            .map { findOrCreate(it.removePrefix("en:")
            .trim()
            .split('-')
            .joinToString(" ") { word -> word.replaceFirstChar { it.uppercaseChar() } })}
            .toMutableSet()
    }

    fun save(tag: Tag): Tag {
        return tagRepository.save(tag)
    }

    fun saveAll(tags: MutableSet<Tag>): MutableList<Tag> {
        return tagRepository.saveAll(tags)
    }
}
