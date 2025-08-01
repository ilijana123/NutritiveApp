package com.example.nutritive_app.controller

import com.example.nutritive_app.entity.Additive
import com.example.nutritive_app.repository.AdditiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/additives")
class AdditiveController {

    @Autowired
    lateinit var additiveRepository: AdditiveRepository

    @PostMapping
    fun createAdditive(@RequestBody additive: Additive): Additive {
        return additiveRepository.save(additive)
    }

    @PutMapping("/{id}")
    fun updateAdditive(@PathVariable id: Long, @RequestBody updated: Additive): ResponseEntity<Additive> {
        return additiveRepository.findById(id).map { existing ->
            val updatedAdditive = existing.copy(name = updated.name)
            ResponseEntity.ok(additiveRepository.save(updatedAdditive))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteAdditive(@PathVariable id: Long) {
        additiveRepository.findById(id).map { additive ->
            print("Additive found $additive and deleted")
            additiveRepository.delete(additive)
            ResponseEntity<Void>(HttpStatus.NO_CONTENT)
        }.orElse(ResponseEntity.notFound().build())
    }
}