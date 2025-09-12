package com.example.nutritive_app.controller

import com.example.nutritive_app.entity.Tag
import com.example.nutritive_app.service.TagService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tags")
class TagController(val tagService: TagService) {

    @GetMapping
    fun getAllTags(): List<Tag> = tagService.getAllTags()

    @GetMapping("/{id}")
    fun getTagsById(@PathVariable("id") tagId: Long): Tag =
        tagService.getTagsById(tagId)

    @PostMapping("/create")
    fun createTag(@RequestBody payload: Tag): Tag = tagService.createTag(payload)

    @PutMapping("/{id}")
    fun updateTagById(@PathVariable("id") tagId: Long, @RequestBody payload: Tag): Tag =
        tagService.updateTagById(tagId, payload)

    @DeleteMapping("/{id}")
    fun deleteTagsById(@PathVariable("id") tagId: Long): Unit =
        tagService.deleteTagsById(tagId)
}
