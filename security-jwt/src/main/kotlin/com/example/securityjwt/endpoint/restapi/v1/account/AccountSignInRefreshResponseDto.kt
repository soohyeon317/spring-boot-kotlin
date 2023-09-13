package com.example.securityjwt.endpoint.restapi.v1.account

import com.example.securityjwt.domain.authtoken.AuthToken

data class AccountSignInRefreshResponseDto(
    val accessToken: String,
    val refreshToken: String
) {

    constructor(authToken: AuthToken): this(
        accessToken = authToken.accessToken,
        refreshToken = authToken.refreshToken
    )
}
