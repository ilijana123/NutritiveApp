package com.example.nutritive_app.service

import com.example.nutritive_app.entity.User
import com.example.nutritive_app.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class AuthenticationService : UserDetailsService {
    @Autowired
    var jwtUserRepository: UserRepository? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user: User = jwtUserRepository!!.findUserByEmail(email)
            .orElseThrow { UsernameNotFoundException("User with email $email not found") }

        val authorities = user.roles.map { SimpleGrantedAuthority("ROLE_$it") }
        println("Authorities: $authorities")

        return org.springframework.security.core.userdetails.User(
            user.email,
            user.password,
            authorities
        )
    }
}