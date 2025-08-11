package com.example.nutritive_app.service

import com.example.nutritive_app.dto.ProductDTO
import com.example.nutritive_app.entity.Product
import com.example.nutritive_app.mapper.ProductMapper
import com.example.nutritive_app.repository.ProductRepository
import com.example.nutritive_app.exception.ProductNotFoundException
import jakarta.transaction.Transactional
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
) {

    fun getAllProducts(): List<ProductDTO> =
        productRepository.findAll().map { productMapper.toDto(it) }

    fun getProductsById(barcode: Long): ProductDTO {
        val product = productRepository.findById(barcode)
            .orElseThrow { ProductNotFoundException(HttpStatus.NOT_FOUND, "No matching product was found") }
        return productMapper.toDto(product)
    }

    @Transactional
    fun createProduct(dto: ProductDTO): ProductDTO {
        val entity = Product(
            barcode = dto.code,
            name = dto.brands ?: "",
            image_url = dto.image_url ?: "",
            nutriments = dto.nutriments?.let { nutrimentService.findOrCreate(it) }
        )

        productRepository.save(entity)

        dto.ingredients_analysis_tags?.let { tagNames ->
            val tags = tagService.findOrCreateAll(tagNames)
            tags.forEach { tag ->
                tag.products.clear()
                entity.tags.add(tag)
                tag.products.add(entity)
            }
            tagService.saveAll(tags)
        }

        dto.categories_tags?.let { categoryNames ->
            val categories = categoryService.findOrCreateAll(categoryNames)
            categories.forEach { category ->
                category.products.clear()
                entity.categories.add(category)
                category.products.add(entity)
            }
            categoryService.saveAll(categories.toList())
        }

        dto.allergens_hierarchy?.let { allergenNames ->
            val allergens = allergenService.findOrCreateAll(allergenNames)
            allergens.forEach { allergen ->
                allergen.products.clear()
                entity.allergens.add(allergen)
                allergen.products.add(entity)
            }
            allergenService.saveAll(allergens.toList())
        }


        dto.countries_hierarchy?.let { countryNames ->
            val countries = countryService.findOrCreateAll(countryNames)
            countries.forEach { country ->
                country.products.clear()
                entity.countries.add(country)
                country.products.add(entity)
            }
            countryService.saveAll(countries.toList())
        }

        dto.additives_tags?.let { additiveNames ->
            val additives = additiveService.findOrCreateAll(additiveNames)
            additives.forEach { additive ->
                additive.products.clear()
                entity.additives.add(additive)
                additive.products.add(entity)
            }
            additiveService.saveAll(additives.toList())
        }

        val savedProduct = productRepository.save(entity)

        return productMapper.toDto(savedProduct)
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
            //nutriscore = nutriscoreService.findOrCreate(dto.nutriscore)
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
