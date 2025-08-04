package com.example.nutritive_app.exception

import org.springframework.http.HttpStatus

class TagNotFoundException(val statusCode: HttpStatus, val reason: String) : Exception()