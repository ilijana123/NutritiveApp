package com.example.nutritive_app.entity

import jakarta.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "username", nullable = false, unique = true)
    val username: String,
    @Column(name = "first_name", nullable = false)
    var firstName: String,
    @Column(name = "last_name", nullable = false)
    var lastName: String,
    @Column(name = "email", nullable = false, unique = true)
    var email: String,
    @Column(name = "password", nullable = false)
    var password: String,

    @ManyToMany
    @JoinTable(
        name = "user_allergens",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "allergen_id")]
    )
    val allergens: MutableSet<Allergen> = mutableSetOf(),

    @ManyToMany
    @JoinTable(
        name = "user_products",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    val products: MutableSet<Product> = mutableSetOf()
)