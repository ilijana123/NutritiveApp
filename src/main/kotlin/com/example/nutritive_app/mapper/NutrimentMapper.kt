package com.example.nutritive_app.mapper

import com.example.nutritive_app.dto.NutrimentDTO
import com.example.nutritive_app.entity.Nutriment
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface NutrimentMapper {
    fun toEntity(dto: NutrimentDTO): Nutriment
    fun toDto(entity: Nutriment): NutrimentDTO
}