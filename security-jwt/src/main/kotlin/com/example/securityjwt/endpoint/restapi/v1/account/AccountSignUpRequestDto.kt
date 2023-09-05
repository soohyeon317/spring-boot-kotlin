package com.example.securityjwt.endpoint.restapi.v1.account

data class AccountSignUpRequestDto(
    val email: String,
    val password: String
)
