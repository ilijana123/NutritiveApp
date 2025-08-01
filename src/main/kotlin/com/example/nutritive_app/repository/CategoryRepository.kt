package com.example.nutritive_app.repository

import com.example.nutritive_app.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByName(name: String): Category?
}