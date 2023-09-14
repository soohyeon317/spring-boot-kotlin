package com.example.securityjwt.endpoint.restapi.v1.account

import jakarta.validation.constraints.NotBlank

data class AccountSignUpRequestDto(
    @field:NotBlank(message = "NotBlank")
    val email: String?,
    @field:NotBlank(message = "NotBlank")
    val password: String?
)
