package com.example.nutritive_app.repository

import com.example.nutritive_app.entity.Additive
import org.springframework.data.jpa.repository.JpaRepository

interface AdditiveRepository : JpaRepository<Additive, Long> {
    fun findByName(name: String): Additive?
}