package com.example.securityjwt.configuration.authentication

import io.jsonwebtoken.Claims
import java.util.*

data class AuthenticationToken(
    val accountId: Long,
    val expiration: Long,
    val expirationDate: Date,
    val tokenType: AuthenticationTokenType
) {

    companion object {
        const val ACCOUNT_ID_CLAIM_KEY = "accountId"
        const val TOKEN_TYPE_CLAIM_KEY = "tokenType"
        const val BEARER_TOKEN_AUTH_TYPE = "bearer"
    }

    constructor(claims: Claims) : this(
        accountId = claims[ACCOUNT_ID_CLAIM_KEY].toString().toLong(),
        expiration = claims.expiration.toInstant().toEpochMilli(),
        expirationDate = claims.expiration,
        tokenType = AuthenticationTokenType.valueOf(claims[TOKEN_TYPE_CLAIM_KEY] as String)
    )
}
