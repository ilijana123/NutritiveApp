package com.example.nutritive_app.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "nutriments")
data class Nutriment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name="carbohydrates")
    val carbohydrates: Float? = null,
    @Column(name="carbohydrates_100g")
    val carbohydrates_100g: Float? = null,
    @Column(name="energy")
    val energy: Float? = null,
    @Column(name="energy_kj_100g")
    val energy_kj_100g: Float? = null,
    @Column(name="fat")
    val fat: Float? = null,
    @Column(name="fat_100g")
    val fat_100g: Float? = null,
    @Column(name="fiber")
    val fiber: Float? = null,
    @Column(name="fiber_100g")
    val fiber_100g: Float? = null,
    @Column(name="proteins")
    val proteins: Float? = null,
    @Column(name="proteins_100g")
    val proteins_100g: Float? = null,
    @Column(name="salt")
    val salt: Float? = null,
    @Column(name="salt_100g")
    val salt_100g: Float? = null,
    @Column(name="saturated_fat")
    val saturated_fat: Float? = null,
    @Column(name="saturated_fat_100g")
    val saturated_fat_100g: Float? = null,
    @Column(name="sodium")
    val sodium: Float? = null,
    @Column(name="sodium_100g")
    val sodium_100g: Float? = null,
    @Column(name="sugars")
    val sugars: Float? = null,
    @Column(name="sugars_100g")
    val sugars_100g: Float? = null,

    @OneToOne(mappedBy = "nutriments")
    var product: Product? = null
)