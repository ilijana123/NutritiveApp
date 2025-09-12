package com.example.nutritive_app.exception

import org.springframework.http.HttpStatus

class NutrimentNotFoundException(val statusCode: HttpStatus, val reason: String) : Exception()