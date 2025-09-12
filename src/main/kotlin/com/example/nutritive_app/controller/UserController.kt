package com.example.nutritive_app.controller

import com.example.nutritive_app.dto.request.AssignRoleRequest
import com.example.nutritive_app.dto.request.LoginRequest
import com.example.nutritive_app.dto.request.SignUpRequest
import com.example.nutritive_app.dto.response.AuthResponse
import com.example.nutritive_app.jwt.JwtUtil
import com.example.nutritive_app.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/create")
    fun createUser(@RequestBody request: SignUpRequest): ResponseEntity<String> {
        val user = userService.createUser(request)
        logger.info("Created user with email ${user.email}")
        return ResponseEntity.ok("User ${user.email} created")
    }

    @PostMapping("/{userId}/roles")
    fun assignRoleToUser(@PathVariable userId: Long, @RequestParam role: AssignRoleRequest): ResponseEntity<String> {
        userService.assignRoleToUser(userId, role.roleId.toString())
        return ResponseEntity.ok("User with id $userId has been assigned the role $role")
    }

    @GetMapping("/{userId}/roles")
    fun getUserRoles(@PathVariable userId: Long): ResponseEntity<Set<String>> {
        val roles = userService.getUserRoles(userId)
        return ResponseEntity.ok(roles)
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<*> {
        return try {
            val auth: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(request.email, request.password)
            )
            val userDetails = auth.principal as UserDetails
            val user = userService.getUserByEmail(userDetails.username) // fetch actual user
            val token = jwtUtil.generateToken(userDetails.username)
            ResponseEntity.ok(AuthResponse(user.id!!, user.email, token))
        } catch (_: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password")
        }
    }
}
