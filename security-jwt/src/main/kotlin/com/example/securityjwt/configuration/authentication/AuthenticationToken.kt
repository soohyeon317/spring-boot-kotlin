package com.example.securityjwt.configuration.authentication

import io.jsonwebtoken.Claims
import java.util.*

data class AuthenticationToken(
    val issuer: String,
    val ownerId: Long,
    val expiration: Long,
    val expirationDate: Date,
    val tokenType: AuthenticationTokenType
) {

    companion object {
        const val OWNER_ID_CLAIM_KEY = "ownerId"
        const val TOKEN_TYPE_CLAIM_KEY = "tokenType"
        const val TOKEN_GRANT_TYPE = "bearer"
    }

    constructor(claims: Claims) : this(
        issuer = claims.issuer,
        ownerId = claims[OWNER_ID_CLAIM_KEY].toString().toLong(),
        expiration = claims.expiration.toInstant().toEpochMilli(),
        expirationDate = claims.expiration,
        tokenType = AuthenticationTokenType.valueOf(claims[TOKEN_TYPE_CLAIM_KEY] as String)
    )
}
