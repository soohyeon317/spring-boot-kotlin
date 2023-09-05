package com.example.securityjwt.endpoint.restapi.v1.account

import kotlinx.serialization.Serializable

@Serializable
data class AccountSignInRequestDto(
    val email: String,
    val password: String
)
