package com.example.securityjwt.endpoint.restapi.v1.account

import jakarta.validation.constraints.NotBlank

data class AccountSignInRefreshRequestDto(
    @field:NotBlank(message = "NotBlank")
    val accessToken: String?,
    @field:NotBlank(message = "NotBlank")
    val refreshToken: String?
)
