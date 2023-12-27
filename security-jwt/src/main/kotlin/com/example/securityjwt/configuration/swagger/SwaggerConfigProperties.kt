package com.example.securityjwt.configuration.swagger

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "springdoc")
data class SwaggerConfigProperties(
    val serverUrl: String
)
