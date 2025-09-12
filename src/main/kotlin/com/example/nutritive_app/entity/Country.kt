package com.example.nutritive_app.entity

import jakarta.persistence.*

@Entity
@Table(name = "country")
data class Country(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name="name", nullable = false)
    var name: String,

    @ManyToMany
    @JoinTable(
        name = "product_countries",
        joinColumns = [JoinColumn(name = "country_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    val products: MutableSet<Product> = mutableSetOf()
)