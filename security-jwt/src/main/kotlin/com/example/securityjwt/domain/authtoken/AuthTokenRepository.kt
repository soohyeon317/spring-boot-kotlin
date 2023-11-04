package com.example.securityjwt.domain.authtoken

interface AuthTokenRepository {

    suspend fun save(authToken: AuthToken, willInactivate: Boolean? = null): AuthToken
    suspend fun finfindTopByAccessTokenAndDeletedAtIsNullOrderByIdDescBy(accessToken: String): AuthToken?
    suspend fun findTopByAccountIdAndAccessTokenAndDeletedAtIsNullOrderByIdDesc(accountId: Long, accessToken: String): AuthToken?
    suspend fun findAllByAccountIdAndDeletedAtIsNull(accountId: Long): List<AuthToken>
}
