package com.example.securityjwt.endpoint.restapi.v1.account

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class AccountSignUpRequestDto(
    @field:NotBlank(message = "NotBlank")
    val email: String? = null,
    @field:NotBlank(message = "NotBlank")
    val password: String? = null
)
