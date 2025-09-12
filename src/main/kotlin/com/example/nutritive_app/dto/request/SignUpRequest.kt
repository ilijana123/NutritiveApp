package com.example.nutritive_app.dto.request

data class SignUpRequest(
    var email: String,
    var password: String,
    var username: String,
    var firstName: String,
    var lastName: String
)