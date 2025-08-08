package com.example.nutritive_app.controller

import com.example.nutritive_app.entity.Category
import com.example.nutritive_app.service.CategoryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    fun getAllCategories(): List<Category> = categoryService.getAllCategories()

    @GetMapping("/{id}")
    fun getCategoriesById(@PathVariable("id") categoryId: Long): Category =
        categoryService.getCategoriesById(categoryId)

    @PostMapping
    fun createCategory(@RequestBody payload: Category): Category = categoryService.createCategory(payload)

    @PutMapping("/{id}")
    fun updateCategoryById(@PathVariable("id") categoryId: Long, @RequestBody payload: Category): Category =
        categoryService.updateCategoryById(categoryId, payload)


    @DeleteMapping("/{id}")
    fun deleteCategoriesById(@PathVariable("id") categoryId: Long): Unit =
        categoryService.deleteCategoriesById(categoryId)
}