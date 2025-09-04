package com.example.nutritive_app.service

import com.example.nutritive_app.entity.Allergen
import com.example.nutritive_app.repository.AllergenRepository
import com.example.nutritive_app.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserAllergenService(
    private val userRepository: UserRepository,
    private val allergenRepository: AllergenRepository
) {

    fun getUserAllergens(userId: Long): Set<Allergen> {
        val user = userRepository.findById(userId).orElseThrow {
            NoSuchElementException("User $userId not found")
        }
        return user.allergens
    }

    @Transactional
    fun replaceUserAllergens(userId: Long, allergenIds: List<Long>): Set<Allergen> {
        val user = userRepository.findById(userId).orElseThrow {
            NoSuchElementException("User $userId not found")
        }
        val newAllergens = allergenRepository.findAllById(allergenIds).toMutableSet()

        user.allergens.clear()
        user.allergens.addAll(newAllergens)
        userRepository.save(user)

        return user.allergens
    }

    @Transactional
    fun addUserAllergen(userId: Long, allergenId: Long): Set<Allergen> {
        val user = userRepository.findById(userId).orElseThrow {
            NoSuchElementException("User $userId not found")
        }
        val allergen = allergenRepository.findById(allergenId).orElseThrow {
            NoSuchElementException("Allergen $allergenId not found")
        }

        user.allergens.add(allergen)
        userRepository.save(user)

        return user.allergens
    }

    @Transactional
    fun removeUserAllergen(userId: Long, allergenId: Long): Set<Allergen> {
        val user = userRepository.findById(userId).orElseThrow {
            NoSuchElementException("User $userId not found")
        }

        user.allergens.removeIf { it.id == allergenId }
        userRepository.save(user)

        return user.allergens
    }

    fun getUserAllergensByEmail(email: String): Set<Allergen> {
        val user = userRepository.findByEmail(email)
            ?: throw NoSuchElementException("User with email $email not found")
        return user.allergens
    }

    @Transactional
    fun replaceUserAllergensByEmail(email: String, allergenIds: List<Long>): Set<Allergen> {
        val user = userRepository.findByEmail(email)
            ?: throw NoSuchElementException("User with email $email not found")
        val newAllergens = allergenRepository.findAllById(allergenIds).toMutableSet()

        user.allergens.clear()
        user.allergens.addAll(newAllergens)
        userRepository.save(user)

        return user.allergens
    }
}