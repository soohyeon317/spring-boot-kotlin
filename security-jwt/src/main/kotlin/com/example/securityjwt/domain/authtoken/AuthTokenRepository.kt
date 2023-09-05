package com.example.securityjwt.domain.authtoken

import org.springframework.stereotype.Repository

@Repository
interface AuthTokenRepository {

    suspend fun save(authToken: AuthToken, inactivate: Boolean? = null): AuthToken
    suspend fun findBy(accessToken: String): AuthToken?
    suspend fun findBy(accountId: Long, accessToken: String): AuthToken?
}
