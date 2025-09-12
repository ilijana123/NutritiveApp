package com.example.nutritive_app.service

import com.example.nutritive_app.repository.ProductRepository
import com.example.nutritive_app.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserProductService(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository,
    private val snsService: SnsService
) {
    fun checkAndNotify(userId: Long, barcode: String) {
        val product = productRepository.findByBarcode(barcode)
            ?: throw RuntimeException("Product not found")

        val user = userRepository.findById(userId)
            .orElseThrow { RuntimeException("User not found") }

        val productAllergenNames = product.allergens.map { it.name }
        val userAllergenNames = user.allergens.map { it.name }

        val matched = productAllergenNames.intersect(userAllergenNames.toSet())
        if (matched.isNotEmpty()) {
            snsService.sendAllergenAlert(
                endpointArn = user.snsEndpointArn!!,
                productName = product.name,
                allergens = productAllergenNames
            )
        }
    }
}
