package com.example.nutritive_app.repository

import com.example.nutritive_app.entity.Nutriscore
import org.springframework.data.jpa.repository.JpaRepository

interface NutriscoreRepository : JpaRepository<Nutriscore, Long> {
}