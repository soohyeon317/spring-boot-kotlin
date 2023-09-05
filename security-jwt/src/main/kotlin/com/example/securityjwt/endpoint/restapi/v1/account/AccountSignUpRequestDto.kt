package com.example.securityjwt.endpoint.restapi.v1.account

import kotlinx.serialization.Serializable

@Serializable
data class AccountSignUpRequestDto(
    val email: String,
    val password: String
)
