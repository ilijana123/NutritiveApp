package com.example.nutritive_app.controller

import com.example.nutritive_app.service.etl.ProductFetcher
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products/import")
class ProductImportController(
    private val productFetcher: ProductFetcher
) {
    @GetMapping
    fun triggerImport(): ResponseEntity<String> {
        productFetcher.fetchAndImportProducts()
        return ResponseEntity.ok("Import triggered.")
    }
}
