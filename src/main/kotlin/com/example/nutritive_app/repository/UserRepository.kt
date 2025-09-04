package com.example.nutritive_app.repository

import com.example.nutritive_app.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    override fun findAll(): List<User>
    fun findByUsername(username: String): User?
    fun findUserByEmail(email: String): Optional<User>
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?
}