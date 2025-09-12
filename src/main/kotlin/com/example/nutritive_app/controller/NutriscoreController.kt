package com.example.nutritive_app.controller

import com.example.nutritive_app.entity.Nutriscore
import com.example.nutritive_app.service.NutriscoreService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/nutriscores")
class NutriscoreController(private val nutriscoreService: NutriscoreService) {

    @GetMapping
    fun getAllNutriscores(): List<Nutriscore> = nutriscoreService.getAllNutriscores()

    @GetMapping("/{id}")
    fun getNutriscoresById(@PathVariable("id") nutriscoreId: Long): Nutriscore =
        nutriscoreService.getNutriscoresById(nutriscoreId)

    @PostMapping
    fun createNutriscore(@RequestBody payload: Nutriscore): Nutriscore = nutriscoreService.createNutriscore(payload)

    @PutMapping("/{id}")
    fun updateNutriscoreById(@PathVariable("id") nutriscoreId: Long, @RequestBody payload: Nutriscore): Nutriscore =
        nutriscoreService.updateNutriscoreById(nutriscoreId, payload)


    @DeleteMapping("/{id}")
    fun deleteNutriscoresById(@PathVariable("id") nutriscoreId: Long): Unit =
        nutriscoreService.deleteNutriscoresById(nutriscoreId)
}