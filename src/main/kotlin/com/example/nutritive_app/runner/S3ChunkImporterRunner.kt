package com.example.nutritive_app.runner

import com.example.nutritive_app.service.etl.S3ChunkImporterService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class S3ChunkImporterRunner(
    private val importerService: S3ChunkImporterService
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        if (System.getenv("RUN_S3_IMPORT") == "true") {
            importerService.importAllChunks()
        } else {
            println("⏩ Skipping S3 import (set RUN_S3_IMPORT=true to enable).")
        }
    }
}
