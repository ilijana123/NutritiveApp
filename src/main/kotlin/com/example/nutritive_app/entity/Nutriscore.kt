package com.example.nutritive_app.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "nutriscore")
data class Nutriscore(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name="grade")
    val grade: String? = null,
    @Column(name="score")
    val score: Int? = null,
    @Column(name="positive_points")
    val positivePoints: Int?,
    @Column(name="negative_points")
    val negativePoints: Int?,

    @OneToOne(mappedBy = "nutriscore")
    var product: Product? = null
)