package com.example.nutritive_app.controller

import com.example.nutritive_app.entity.Nutriment
import com.example.nutritive_app.service.NutrimentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/nutriments")
class NutrimentController(private val nutrimentService: NutrimentService) {

    @GetMapping
    fun getAllNutriments(): List<Nutriment> = nutrimentService.getAllNutriments()

    @GetMapping("/{id}")
    fun getNutrimentsById(@PathVariable("id") nutrimentId: Long): Nutriment =
        nutrimentService.getNutrimentsById(nutrimentId)

    @PostMapping
    fun createNutriment(@RequestBody payload: Nutriment): Nutriment = nutrimentService.createNutriment(payload)

    @PutMapping("/{id}")
    fun updateNutrimentById(@PathVariable("id") nutrimentId: Long, @RequestBody payload: Nutriment): Nutriment =
        nutrimentService.updateNutrimentsById(nutrimentId, payload)


    @DeleteMapping("/{id}")
    fun deleteNutrimentsById(@PathVariable("id") nutrimentId: Long): Unit =
        nutrimentService.deleteNutrimentsById(nutrimentId)
}