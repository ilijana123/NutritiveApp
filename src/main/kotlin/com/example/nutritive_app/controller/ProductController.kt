package com.example.nutritive_app.controller

import com.example.nutritive_app.dto.ProductDTO
import com.example.nutritive_app.dto.ProductDetailsDTO
import com.example.nutritive_app.service.ProductService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getAllProducts(): List<ProductDTO> = productService.getAllProducts()

    @GetMapping("/{id}")
    fun getProductsById(@PathVariable("id") barcode: Long): ProductDetailsDTO =
        productService.getProductsById(barcode)

    @PostMapping
    fun createProduct(@RequestBody dto: ProductDTO): ProductDTO =
        productService.createProduct(dto)

    @PutMapping("/{id}")
    fun updateProductById(@PathVariable("id") barcode: Long, @RequestBody dto: ProductDTO): ProductDTO =
        productService.updateProductById(barcode, dto)

    @DeleteMapping("/{id}")
    fun deleteProductsById(@PathVariable("id") barcode: Long) =
        productService.deleteProductsById(barcode)
}
