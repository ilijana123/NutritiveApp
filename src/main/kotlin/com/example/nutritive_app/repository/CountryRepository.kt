package com.example.nutritive_app.repository

import com.example.nutritive_app.entity.Country
import org.springframework.data.jpa.repository.JpaRepository

interface CountryRepository : JpaRepository<Country, Long> {
    fun findByName(name: String): Country?
}