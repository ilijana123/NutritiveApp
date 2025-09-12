package com.example.nutritive_app.entity

import jakarta.persistence.*

@Entity
data class Additive(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name="name", nullable = false)
    var name: String,

    @ManyToMany
    @JoinTable(
    name = "product_additives",
    joinColumns = [JoinColumn(name = "additive_id")],
    inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    val products: MutableSet<Product> = mutableSetOf()
)