package com.example.nutritive_app.controller

import com.example.nutritive_app.service.etl.ProductFetcher
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


// ETL Pipeline (Lambda) used instead of this
@RestController
@RequestMapping("/import")
class ProductImportController(
    private val productFetcher: ProductFetcher
) {
    @GetMapping
    fun triggerImport(request: HttpServletRequest): ResponseEntity<String> {
        println("HIT /products/import with JWT: ${request.getHeader("Authorization")}")
        productFetcher.fetchAndImportProducts()
        return ResponseEntity.ok("Import triggered.")
    }
}
