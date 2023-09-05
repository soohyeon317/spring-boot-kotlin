package com.example.securityjwt.infrastructure.persistence.jpa.authtoken

import org.springframework.data.r2dbc.repository.R2dbcRepository

interface SpringDataAuthTokenRepository : R2dbcRepository<AuthTokenEntity, Long> {

    suspend fun findTopByAccessTokenAndDeletedAtIsNullOrderByIdDesc(accessToken: String): AuthTokenEntity?
    suspend fun findTopByAccountIdAndAccessTokenAndDeletedAtIsNullOrderByIdDesc(accountId: Long, accessToken: String): AuthTokenEntity?
}
