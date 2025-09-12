package com.example.nutritive_app.exception

import org.springframework.http.HttpStatus

class CountryNotFoundException(val statusCode: HttpStatus, val reason: String) : Exception()