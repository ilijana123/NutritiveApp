package com.example.nutritive_app.entity

import jakarta.persistence.*

@Entity
data class Allergen(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name="name", nullable = false)
    var name: String,

    @ManyToMany
    @JoinTable(
        name = "product_allergens",
        joinColumns = [JoinColumn(name = "allergen_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    val products: MutableSet<Product> = mutableSetOf()
)