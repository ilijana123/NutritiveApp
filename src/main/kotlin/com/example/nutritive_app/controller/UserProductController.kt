package com.example.nutritive_app.controller

import com.example.nutritive_app.service.UserProductService
import com.example.nutritive_app.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/products")
class UserProductController(
    private val userProductService: UserProductService,
    private val userService: UserService
) {
    @PostMapping("/{barcode}/check-allergens")
    fun checkAllergens(
        @PathVariable barcode: String,
        principal: Principal
    ): ResponseEntity<Void> {
        val user = userService.getUserByUsername(principal.name)
        userProductService.checkAndNotify(user.id!!, barcode)
        return ResponseEntity.ok().build()
    }
}
