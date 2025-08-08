package com.example.nutritive_app.service

import com.example.nutritive_app.dto.ProductDTO
import com.example.nutritive_app.mapper.ProductMapper
import com.example.nutritive_app.repository.ProductRepository
import com.example.nutritive_app.exception.ProductNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper,
    private val tagService: TagService,
    private val categoryService: CategoryService,
    private val allergenService: AllergenService,
    private val countryService: CountryService,
    private val additiveService: AdditiveService,
    private val nutrimentService: NutrimentService,
    private val nutriscoreService: NutriscoreService,
) {

    fun getAllProducts(): List<ProductDTO> =
        productRepository.findAll().map { productMapper.toDto(it) }

    fun getProductsById(barcode: Long): ProductDTO {
        val product = productRepository.findById(barcode)
            .orElseThrow { ProductNotFoundException(HttpStatus.NOT_FOUND, "No matching product was found") }
        return productMapper.toDto(product)
    }

    fun createProduct(dto: ProductDTO): ProductDTO {
        val entity = productMapper.toEntity(
            dto,
            tagService,
            categoryService,
            allergenService,
            countryService,
            additiveService,
            nutrimentService,
            nutriscoreService
        )
        return productMapper.toDto(productRepository.save(entity))
    }

    fun updateProductById(barcode: Long, dto: ProductDTO): ProductDTO {
        val existing = productRepository.findById(barcode)
            .orElseThrow { ProductNotFoundException(HttpStatus.NOT_FOUND, "No matching product was found") }

        val updated = existing.copy(
            name = dto.brands ?: "",
            image_url = dto.image_url ?: "",
            tags = tagService.findOrCreateAll(dto.ingredients_analysis_tags ?: emptyList()).toMutableSet(),
            categories = categoryService.findOrCreateAll(dto.categories_tags ?: emptyList()).toMutableSet(),
            allergens = allergenService.findOrCreateAll(dto.allergens_hierarchy ?: emptyList()).toMutableSet(),
            countries = countryService.findOrCreateAll(dto.countries_hierarchy ?: emptyList()).toMutableSet(),
            additives = additiveService.findOrCreateAll(dto.additives_tags ?: emptyList()).toMutableSet(),
            nutriments = nutrimentService.findOrCreate(dto.nutriments),
            nutriscore = nutriscoreService.extractAndSaveFromJson(dto.nutriscore)
        )

        return productMapper.toDto(productRepository.save(updated))
    }

    fun deleteProductsById(barcode: Long) {
        if (!productRepository.existsById(barcode)) {
            throw ProductNotFoundException(HttpStatus.NOT_FOUND, "No matching product was found")
        }
        productRepository.deleteById(barcode)
    }
}