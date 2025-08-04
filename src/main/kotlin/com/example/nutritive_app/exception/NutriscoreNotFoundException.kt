package com.example.nutritive_app.exception

import org.springframework.http.HttpStatus

class NutriscoreNotFoundException(val statusCode: HttpStatus, val reason: String) : Exception()