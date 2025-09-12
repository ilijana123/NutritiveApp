package com.example.nutritive_app.repository

import com.example.nutritive_app.entity.Nutriment
import org.springframework.data.jpa.repository.JpaRepository

interface NutrimentRepository : JpaRepository<Nutriment, Long> {
}