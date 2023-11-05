package com.example.securityjwt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.transaction.interceptor.TransactionInterceptor
import org.springframework.web.reactive.config.EnableWebFlux
import reactor.blockhound.BlockHound
import java.util.*

@EnableWebFlux
@EnableR2dbcRepositories
@SpringBootApplication
@ConfigurationPropertiesScan
class SecurityJwtApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    val props: Properties = System.getProperties()
    if (props["blockhound"] == "on") {
        println("Block hound installed.")
        BlockHound.install({
            it.allowBlockingCallsInside(
                "org.springframework.validation.beanvalidation.SpringValidatorAdapter",
                "validate"
            )
            it.allowBlockingCallsInside(
                TransactionInterceptor::class.java.name,
                "invoke"
            )
        })
    }
    runApplication<SecurityJwtApplication>(*args)
}
