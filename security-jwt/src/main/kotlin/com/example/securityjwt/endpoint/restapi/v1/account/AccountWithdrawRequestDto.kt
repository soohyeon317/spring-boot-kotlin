package com.example.securityjwt.endpoint.restapi.v1.account

import jakarta.validation.constraints.NotBlank

data class AccountWithdrawRequestDto(
    @field:NotBlank(message = "NotBlank")
    val password: String?
)
