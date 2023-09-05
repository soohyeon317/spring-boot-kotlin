package com.example.securityjwt.endpoint.restapi.v1.account

import kotlinx.serialization.Serializable

@Serializable
data class AccountSignOutRequestDto(
    val accessToken: String
)
