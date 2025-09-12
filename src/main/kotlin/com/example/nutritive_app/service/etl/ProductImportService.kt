package com.example.nutritive_app.service.etl

import com.example.nutritive_app.dto.ProductDTO
import com.example.nutritive_app.mapper.ProductMapper
import com.example.nutritive_app.repository.ProductRepository
import com.example.nutritive_app.service.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

// ETL Pipeline (Lambda) used instead of this
@Service
class ProductImportService(
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

    @Transactional
    fun import(dto: ProductDTO) {
        val product = productMapper.toEntity(
            dto,
            tagService,
            categoryService,
            allergenService,
            countryService,
            additiveService,
            nutrimentService,
            nutriscoreService,
        )

        val savedProduct = productRepository.save(product)

        savedProduct.allergens.forEach { it.products.add(savedProduct) }
        savedProduct.categories.forEach { it.products.add(savedProduct) }
        savedProduct.tags.forEach { it.products.add(savedProduct) }
        savedProduct.additives.forEach { it.products.add(savedProduct) }
        savedProduct.countries.forEach { it.products.add(savedProduct) }
        savedProduct.nutriments?.product = savedProduct
        savedProduct.nutriscore?.product = savedProduct
    }
}