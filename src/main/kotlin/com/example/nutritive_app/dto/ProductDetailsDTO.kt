package com.example.nutritive_app.dto

data class ProductDetailsDTO(
    val barcode: Long?,
    val name: String,
    val imageUrl: String?,

    val nutriments: NutrimentDTO? = null,
    val nutriscore: NutriscoreDTO? = null,

    val additives: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val countries: List<String> = emptyList(),
    val allergens: List<String> = emptyList(),
    val categories: List<String> = emptyList()
)