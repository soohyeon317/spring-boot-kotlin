package com.example.securityjwt.endpoint.restapi.v1.account

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class AccountSignOutRequestDto(
    @field:NotBlank(message = "NotBlank")
    val accessToken: String? = null
)
