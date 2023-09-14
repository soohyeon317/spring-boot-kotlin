package com.example.securityjwt.endpoint.restapi.v1.account

import com.example.securityjwt.domain.enumeration.DisplayLanguage
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AccountSignInRequestDto(
    @field:NotBlank(message = "NotBlank")
    val email: String?,
    @field:NotBlank(message = "NotBlank")
    val password: String?,
    @field:NotNull(message = "NotNull")
    val displayLanguage: DisplayLanguage?
)
