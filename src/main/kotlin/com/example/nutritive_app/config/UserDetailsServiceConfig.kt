package com.example.nutritive_app.config

import com.example.nutritive_app.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService

@Configuration
class UserDetailsServiceConfig(private val userRepository: UserRepository) {

    @Bean
    fun userDetailsService(): UserDetailsService = UserDetailsService { username ->
        val user = userRepository.findByUsername(username)
            ?: throw RuntimeException("User not found")
        User
            .withUsername(user.username)
            .password(user.password)
            .roles(*user.roles.toTypedArray())
            .build()
    }
}