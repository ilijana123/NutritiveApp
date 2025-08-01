package com.example.nutritive_app.service.etl

import com.example.nutritive_app.dto.ProductDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.zip.GZIPInputStream

@Component
class ProductFetcher(
    private val productImportService: ProductImportService,
    private val objectMapper: ObjectMapper
) {
    private val dataUrl = "https://static.openfoodfacts.org/data/openfoodfacts-products.jsonl.gz"

    private val httpClient = HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .build()

    // For testing remove scheduled annotation and use the endpoint for the product import
    // @Scheduled(cron = "0 0 0 * * MON")

//    fun fetchAndImportProducts() {
//        println("Starting OpenFoodFacts import...")
//
//        try {
//            val request = HttpRequest.newBuilder()
//                .uri(URI.create(dataUrl))
//                .GET()
//                .build()
//
//            val response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream())
//
//            if (response.statusCode() != 200) {
//                println("Failed to fetch data: HTTP ${response.statusCode()}")
//                return
//            }
//
//            GZIPInputStream(response.body()).bufferedReader().useLines { lines ->
//                val line = lines.firstOrNull()
//                if (line != null) {
//                    try {
//                        val jsonNode = objectMapper.readTree(line)
//                        val productJson = jsonNode.get("product") ?: jsonNode // depends on actual format
//                        val productDto = objectMapper.treeToValue(productJson, ProductDTO::class.java)
//                        productImportService.import(productDto)
//                        println("Imported 1 product for testing.")
//                    } catch (e: Exception) {
//                        println("Failed to import product: ${e.message}")
//                    }
//                } else {
//                    println("No lines found in file.")
//                }
//            }
//
//            println("OpenFoodFacts import complete.")
//        } catch (e: Exception) {
//            println("Error during OpenFoodFacts import: ${e.message}")
//        }
//    }

    fun fetchAndImportProducts() {
        println("Starting OpenFoodFacts import...")

        try {
            val request = HttpRequest.newBuilder()
                .uri(URI.create(dataUrl))
                .GET()
                .build()

            val response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream())

            if (response.statusCode() != 200) {
                println("Failed to fetch data: HTTP ${response.statusCode()}")
                return
            }

            GZIPInputStream(response.body()).bufferedReader().useLines { lines ->
                var counter = 0
                lines.forEach { line ->
                    try {
                        val jsonNode = objectMapper.readTree(line)
                        val productDto = objectMapper.readValue(line, ProductDTO::class.java)
                        productImportService.import(productDto)
                        counter++
                        if (counter % 100 == 0) println("Imported $counter products...")
                    } catch (e: Exception) {
                        println("Skipping invalid line: ${e.message}")
                    }
                }
                println("Total imported: $counter")
            }

            println("OpenFoodFacts import complete.")
        } catch (e: Exception) {
            println("Error during OpenFoodFacts import: ${e.message}")
        }
    }
}