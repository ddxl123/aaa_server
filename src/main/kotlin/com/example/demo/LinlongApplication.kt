package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext

lateinit var context: ConfigurableApplicationContext

@SpringBootApplication
class LinlongApplication

fun main(args: Array<String>) {
    context = runApplication<LinlongApplication>(*args)
}
