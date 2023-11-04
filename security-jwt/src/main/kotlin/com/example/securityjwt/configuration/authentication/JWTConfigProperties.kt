package com.example.securityjwt.configuration.authentication

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JWTConfigProperties(
    val secretKey: String,
    val accessTokenValidityInSeconds: Int,
    val refreshTokenValidityInSeconds: Int
)
