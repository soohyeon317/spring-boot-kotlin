package com.example.securityjwt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class SecurityJwtApplication

fun main(args: Array<String>) {

    @Suppress("SpreadOperator")
    runApplication<SecurityJwtApplication>(*args)
}
