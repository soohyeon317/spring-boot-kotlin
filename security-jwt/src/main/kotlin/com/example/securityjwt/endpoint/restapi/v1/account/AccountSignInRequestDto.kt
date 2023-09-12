package com.example.securityjwt.endpoint.restapi.v1.account

import com.example.securityjwt.domain.enumeration.DisplayLanguage

data class AccountSignInRequestDto(
    val email: String,
    val password: String,
    val displayLanguage: DisplayLanguage
)
