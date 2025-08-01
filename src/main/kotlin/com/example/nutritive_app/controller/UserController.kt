package com.example.nutritive_app.controller

import com.example.nutritive_app.repository.UserRepository
import com.example.nutritive_app.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {
    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping
    fun getAllUsers(): List<User> = userRepository.findAll()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> =
        userRepository.findById(id).map { user ->
            ResponseEntity.ok(user)
        }.orElse(ResponseEntity.notFound().build())

    @PostMapping
    fun createUser(@RequestBody user: User): User = userRepository.save(user)

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody updatedUser: User): ResponseEntity<User> =
        userRepository.findById(id).map { existingUser ->
            val userToUpdate = existingUser.copy(
                firstName = updatedUser.firstName,
                lastName = updatedUser.lastName,
                email = updatedUser.email,
                password = updatedUser.password
            )
            ResponseEntity.ok(userRepository.save(userToUpdate))
        }.orElse(ResponseEntity.notFound().build())

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> =
        userRepository.findById(id).map { user ->
            print("User found $user and deleted")
            userRepository.delete(user)
            ResponseEntity<Void>(HttpStatus.NO_CONTENT)
        }.orElse(ResponseEntity.notFound().build())
}