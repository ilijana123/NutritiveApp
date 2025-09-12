package com.example.nutritive_app.service.etl

import com.example.nutritive_app.dto.ProductDTO
import com.example.nutritive_app.service.ProductService
import com.example.nutritive_app.repository.ProductRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import java.util.zip.GZIPInputStream

@Service
class S3ChunkImporterService(
    private val productService: ProductService,
    private val productRepository: ProductRepository,
    private val objectMapper: ObjectMapper,
) {
    private val bucketName = "nutritiveappbucket"
    private val prefix = "data/"
    private val s3 = S3Client.create()

    fun importAllChunks(): String {
        val objects = s3.listObjectsV2 { it.bucket(bucketName).prefix(prefix) }
        var totalImported = 0
        var totalSkipped = 0

        for (obj in objects.contents()) {
            if (!obj.key().endsWith(".gz")) continue

            println("📦 Processing ${obj.key()} ...")
            val getReq = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(obj.key())
                .build()

            s3.getObject(getReq).use { s3Stream ->
                GZIPInputStream(s3Stream).bufferedReader().useLines { lines ->
                    var counter = 0
                    var skipped = 0
                    for (line in lines) {
                        try {
                            val dto = objectMapper.readValue(line, ProductDTO::class.java)

                            if (productRepository.existsById(dto.code)) {
                                skipped++
                                continue
                            }

                            productService.createProduct(dto)
                            counter++

                            if ((counter + skipped) % 100 == 0) {
                                println("Imported $counter, skipped $skipped so far...")
                            }
                        } catch (e: Exception) {
                            println("⚠️ Skipping invalid product: ${e.message}")
                        }
                    }
                    println("✅ Finished ${obj.key()} → Imported $counter, skipped $skipped")
                    totalImported += counter
                    totalSkipped += skipped
                }
            }
        }
        val result = "🎉 Import complete → Imported $totalImported, Skipped $totalSkipped"
        println(result)
        return result
    }
}
