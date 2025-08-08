package com.example.nutritive_app.mapper

import com.example.nutritive_app.dto.ProductDTO
import com.example.nutritive_app.entity.*
import com.example.nutritive_app.service.*
import org.mapstruct.Context
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(
    componentModel = "spring",
    uses = [NutrimentMapper::class]
)
interface ProductMapper {

    @Mapping(target = "barcode", source = "code")
    @Mapping(target = "name", source = "brands", defaultValue = "")
    @Mapping(target = "image_url", source = "image_url", defaultValue = "")
    @Mapping(target = "tags", expression = "java(tagService.findOrCreateAll(dto.getIngredients_analysis_tags()))")
    @Mapping(target = "categories", expression = "java(categoryService.findOrCreateAll(dto.getCategories_tags()))")
    @Mapping(target = "allergens", expression = "java(allergenService.findOrCreateAll(dto.getAllergens_hierarchy()))")
    @Mapping(target = "countries", expression = "java(countryService.findOrCreateAll(dto.getCountries_hierarchy()))")
    @Mapping(target = "additives", expression = "java(additiveService.findOrCreateAll(dto.getAdditives_tags()))")
    @Mapping(target = "nutriments", expression = "java(nutrimentService.findOrCreate(dto.getNutriments()))")
    @Mapping(target = "nutriscore", expression = "java(nutriscoreService.extractAndSaveFromJson(dto.getNutriscore()))")
    fun toEntity(
        dto: ProductDTO,
        @Context tagService: TagService,
        @Context categoryService: CategoryService,
        @Context allergenService: AllergenService,
        @Context countryService: CountryService,
        @Context additiveService: AdditiveService,
        @Context nutrimentService: NutrimentService,
        @Context nutriscoreService: NutriscoreService,
    ): Product

    @Mapping(target = "code", source = "barcode")
    @Mapping(target = "brands", source = "name")
    @Mapping(target = "image_url", source = "image_url")

    fun toDto(product: Product): ProductDTO
}