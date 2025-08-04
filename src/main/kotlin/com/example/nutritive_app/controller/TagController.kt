package com.example.nutritive_app.controller

import com.example.nutritive_app.entity.Tag
import com.example.nutritive_app.service.TagService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tags")
class TagController(val tagService: TagService) {

    @GetMapping("/tags")
    fun getAllTags(): List<Tag> = tagService.getAllTags()

    @GetMapping("/tags/{id}")
    fun getTagsById(@PathVariable("id") tagId: Long): Tag =
        tagService.getTagsById(tagId)

    @PostMapping("/tags")
    fun createTag(@RequestBody payload: Tag): Tag = tagService.createTag(payload)

    @PutMapping("/tags/{id}")
    fun updateTagById(@PathVariable("id") tagId: Long, @RequestBody payload: Tag): Tag =
        tagService.updateTagById(tagId, payload)

    @DeleteMapping("/tags/{id}")
    fun deleteTagsById(@PathVariable("id") tagId: Long): Unit =
        tagService.deleteTagsById(tagId)
}
