package com.example.nutritive_app.controller

import com.example.nutritive_app.entity.Category
import com.example.nutritive_app.repository.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categories")
class CategoryController {

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @PostMapping
    fun createCategory(@RequestBody category: Category): Category {
        return categoryRepository.save(category)
    }

    @PutMapping("/{id}")
    fun updateCategory(@PathVariable id: Long, @RequestBody updated: Category): ResponseEntity<Category> {
        return categoryRepository.findById(id).map { existing ->
            existing.name = updated.name
            ResponseEntity.ok(categoryRepository.save(existing))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<Void> {
        return categoryRepository.findById(id).map { category ->
            println("Category found $category and deleted")
            categoryRepository.delete(category)
            ResponseEntity<Void>(HttpStatus.NO_CONTENT)
        }.orElse(ResponseEntity.notFound().build())
    }
}
