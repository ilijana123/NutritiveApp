package com.example.nutritive_app.controller

import com.example.nutritive_app.entity.Tag
import com.example.nutritive_app.repository.TagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tags")
class TagController {

    @Autowired
    lateinit var tagRepository: TagRepository

    @PostMapping
    fun createTag(@RequestBody tag: Tag): Tag {
        return tagRepository.save(tag)
    }

    @GetMapping
    fun getAllTags(): List<Tag> {
        return tagRepository.findAll()
    }

    @GetMapping("/{id}")
    fun getTagById(@PathVariable id: Long): ResponseEntity<Tag> {
        return tagRepository.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/{id}")
    fun updateTag(@PathVariable id: Long, @RequestBody updated: Tag): ResponseEntity<Tag> {
        return tagRepository.findById(id).map { existing ->
            val updatedTag = existing.copy(name = updated.name)
            ResponseEntity.ok(tagRepository.save(updatedTag))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteTag(@PathVariable id: Long): ResponseEntity<Void> {
        return tagRepository.findById(id).map { tag ->
            tagRepository.delete(tag)
            ResponseEntity<Void>(HttpStatus.NO_CONTENT)
        }.orElse(ResponseEntity.notFound().build())
    }
}
