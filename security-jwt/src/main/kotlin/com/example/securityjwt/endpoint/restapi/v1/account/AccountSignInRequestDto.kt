package com.example.securityjwt.endpoint.restapi.v1.account

import com.example.securityjwt.domain.enumeration.SupportLanguage
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import kotlinx.serialization.Serializable

@Serializable
data class AccountSignInRequestDto(
    @field:NotBlank(message = "NotBlank")
    val email: String?,
    @field:NotBlank(message = "NotBlank")
    val password: String?,
    @field:NotNull(message = "NotNull")
    val supportLanguage: SupportLanguage?
)
