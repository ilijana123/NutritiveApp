package com.example.nutritive_app.repository

import com.example.nutritive_app.entity.Allergen
import org.springframework.data.jpa.repository.JpaRepository

interface AllergenRepository : JpaRepository<Allergen, Long> {
    fun findByName(name: String): Allergen?
}