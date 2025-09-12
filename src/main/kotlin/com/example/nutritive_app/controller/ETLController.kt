package com.example.nutritive_app.controller

import com.example.nutritive_app.service.etl.S3ChunkImporterService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/etl")
class ETLController(
    private val importerService: S3ChunkImporterService
) {

    @PostMapping("/import-s3")
    fun importFromS3(): String {
        return importerService.importAllChunks()
    }
}
