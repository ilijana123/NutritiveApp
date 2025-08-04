package com.example.nutritive_app.exception

import org.springframework.http.HttpStatus

class CategoryNotFoundException(val statusCode: HttpStatus, val reason: String) : Exception()