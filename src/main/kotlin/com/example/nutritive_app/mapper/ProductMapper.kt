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
    @Mapping(target = "ingredients_analysis_tags", source = "tags")
    @Mapping(target = "countries_hierarchy", source = "countries")
    @Mapping(target = "allergens_hierarchy", source = "allergens")
    @Mapping(target = "categories_tags", source = "categories")
    @Mapping(target = "additives_tags", source = "additives")
    @Mapping(target = "nutriments", source = "nutriments")
    fun toDto(product: Product): ProductDTO

    fun map(tag: Tag): String = tag.name
    fun map(country: Country): String = country.name
    fun map(allergen: Allergen): String = allergen.name
    fun map(category: Category): String = category.name
    fun map(additive: Additive): String = additive.name
}