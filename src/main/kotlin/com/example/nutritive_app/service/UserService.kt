package com.example.nutritive_app.service

import com.example.nutritive_app.dto.request.SignUpRequest
import com.example.nutritive_app.entity.User
import com.example.nutritive_app.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun createUser(request: SignUpRequest): User {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("User already exists")
        }

        val user = User(
            username = request.username,
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            roles = setOf("USER")
        )

        return userRepository.save(user)
    }

    fun assignRoleToUser(userId: Long, role: String) {
        val user = userRepository.findById(userId).orElseThrow {
            NoSuchElementException("User not found with ID $userId")
        }

        val updatedRoles = user.roles.toMutableSet()
        updatedRoles.add(role)

        val updatedUser = user.copy(roles = updatedRoles)
        userRepository.save(updatedUser)
    }

    fun getUserRoles(userId: Long): Set<String> {
        val user = userRepository.findById(userId).orElseThrow {
            NoSuchElementException("User not found with ID $userId")
        }
        return user.roles
    }

    fun getUserByEmail(email: String): User {
        return userRepository.findUserByEmail(email)
            .orElseThrow { NoSuchElementException("User not found with email $email") }
    }
    fun getUserByUsername(username: String): User {
        return userRepository.findByUsername(username)
    }
}
