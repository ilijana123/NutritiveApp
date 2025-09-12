package com.example.nutritive_app.controller

import com.example.nutritive_app.dto.request.DeviceTokenRequest
import com.example.nutritive_app.service.SnsService
import com.example.nutritive_app.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/devices")
class DeviceController(
    private val snsService: SnsService,
    private val userService: UserService
) {

    @PostMapping("/register")
    fun registerDevice(@RequestBody request: DeviceTokenRequest, principal: Principal): ResponseEntity<Void> {
        val userId = userService.getUserByUsername(principal.name).id
        snsService.registerDeviceToken(request.token, userId!!)
        return ResponseEntity.ok().build()
    }
}
