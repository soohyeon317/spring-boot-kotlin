package com.example.securityjwt.domain.authtoken

import java.time.LocalDateTime

data class AuthToken(
    val id: Long?,
    val accountId: Long,
    val accessToken: String,
    val refreshToken: String,
    val createdAt: LocalDateTime?
) {

    constructor(accountId: Long, accessToken: String, refreshToken: String) :
            this(
                id = null,
                accountId = accountId,
                accessToken = accessToken,
                refreshToken = refreshToken,
                createdAt = null
            )

    fun update(accessToken: String, refreshToken: String): AuthToken = AuthToken(
        id = this.id,
        accountId = this.accountId,
        accessToken = accessToken,
        refreshToken = refreshToken,
        createdAt = this.createdAt
    )
}
