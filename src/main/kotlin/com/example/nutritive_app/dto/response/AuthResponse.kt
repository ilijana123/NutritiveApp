package com.example.nutritive_app.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthResponse(
    @JsonProperty("email") val email: String,
    @JsonProperty("token")val token: String)