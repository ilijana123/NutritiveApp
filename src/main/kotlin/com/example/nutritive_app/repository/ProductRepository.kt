package com.example.nutritive_app.repository

import com.example.nutritive_app.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun findByName(name: String): Product?
    fun findByBarcode(barcode: String): Product?
}