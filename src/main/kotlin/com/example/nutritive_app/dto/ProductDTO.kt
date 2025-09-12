package com.example.nutritive_app.dto

import org.json.JSONObject

data class ProductDTO(
    val code: Long,
    val brands: String?, //name
    val image_url: String?,
    val ingredients_analysis_tags: List<String>?,
    val countries_hierarchy: List<String>?,
    val allergens_hierarchy: List<String>?,
    val categories_tags: List<String>?,
    val additives_tags: List<String>?,
    val nutriments: NutrimentDTO?,
    val nutriscore: JSONObject? = null
)