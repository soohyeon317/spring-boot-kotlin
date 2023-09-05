package com.example.securityjwt.endpoint.restapi.v1.account

data class AccountSignInRequestDto(
    val email: String,
    val password: String
)
