package com.example.securityjwt.endpoint.restapi.v1.account

data class AccountSignInRefreshRequestDto(
    val accessToken: String,
    val refreshToken: String
)
