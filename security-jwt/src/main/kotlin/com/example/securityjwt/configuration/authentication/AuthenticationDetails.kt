package com.example.securityjwt.configuration.authentication

data class AuthenticationDetails(
    val ownerId: Long,
    val accessToken: String
)
