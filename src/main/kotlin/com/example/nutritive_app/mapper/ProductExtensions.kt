package com.example.nutritive_app.mapper

import com.example.nutritive_app.dto.*
import com.example.nutritive_app.entity.*

fun Product.toDetailsDTO(): ProductDetailsDTO =
    ProductDetailsDTO(
        barcode = this.barcode,
        name = this.name,
        imageUrl = this.image_url,
        nutriments = this.nutriments?.let { NutrimentDTO.fromEntity(it) },
        nutriscore = this.nutriscore?.let { NutriscoreDTO.fromEntity(it) },
        additives = this.additives.map { it.name },
        tags = this.tags.map { it.name },
        countries = this.countries.map { it.name },
        allergens = this.allergens.map { it.name },
        categories = this.categories.map { it.name }
    )
