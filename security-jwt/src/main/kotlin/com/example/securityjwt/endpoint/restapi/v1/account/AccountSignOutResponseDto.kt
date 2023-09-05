package com.example.securityjwt.endpoint.restapi.v1.account

import com.example.securityjwt.domain.authtoken.AuthToken

data class AccountSignOutResponseDto(
    val accessToken: String
) {

    constructor(authToken: AuthToken): this(
        accessToken = authToken.accessToken
    )
}
