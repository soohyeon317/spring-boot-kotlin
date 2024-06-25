package com.example.securityjwt.configuration.authentication

interface authenticationTokenManager {

    fun validateToken(token: String, tokenType: AuthenticationTokenType): Boolean
    fun toAuthenticationToken(accessToken: String): AuthenticationToken
    fun createToken(ownerId: Long, tokenType: AuthenticationTokenType): String
    suspend fun isSaved(accessToken: String): Boolean
    suspend fun getAccountId(): Long
    suspend fun getDetails(): AuthenticationDetails
}
