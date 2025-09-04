package com.example.nutritive_app.dto.request

data class UpdateUserAllergensRequest(
    val allergenIds: List<Long> = emptyList()
)