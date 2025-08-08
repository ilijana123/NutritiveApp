package com.example.nutritive_app.controller

import com.example.nutritive_app.entity.Additive
import com.example.nutritive_app.service.AdditiveService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/additives")
class AdditiveController(private val additiveService: AdditiveService) {

    @GetMapping
    fun getAllAdditives(): List<Additive> = additiveService.getAllAdditives()

    @GetMapping("/{id}")
    fun getAdditivesById(@PathVariable("id") additiveId: Long): Additive =
        additiveService.getAdditivesById(additiveId)

    @PostMapping
    fun createAdditive(@RequestBody payload: Additive): Additive = additiveService.createAdditive(payload)

    @PutMapping("/{id}")
    fun updateAdditiveById(@PathVariable("id") additiveId: Long, @RequestBody payload: Additive): Additive =
        additiveService.updateAdditiveById(additiveId, payload)


    @DeleteMapping("/{id}")
    fun deleteAdditivesById(@PathVariable("id") additiveId: Long): Unit =
        additiveService.deleteAdditivesById(additiveId)
}