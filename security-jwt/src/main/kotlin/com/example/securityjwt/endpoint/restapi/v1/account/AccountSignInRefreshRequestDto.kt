package com.example.securityjwt.endpoint.restapi.v1.account

import kotlinx.serialization.Serializable

@Serializable
data class AccountSignInRefreshRequestDto(
    val accessToken: String,
    val refreshToken: String
)
