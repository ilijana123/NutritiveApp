package com.example.nutritive_app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class NutritiveAppApplication

fun main(args: Array<String>) {
	runApplication<NutritiveAppApplication>(*args)
}
