package com.example.jms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class JmsApplication

fun main(args: Array<String>) {

    @Suppress("SpreadOperator")
    runApplication<JmsApplication>(*args)
}
