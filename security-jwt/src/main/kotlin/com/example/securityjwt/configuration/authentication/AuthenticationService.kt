package com.example.securityjwt.configuration.authentication

interface AuthenticationService {

    fun validateToken(token: String, tokenType: AuthenticationTokenType): Boolean
    fun toAuthenticationToken(token: String): AuthenticationToken
    fun createToken(ownerId: Long, tokenType: AuthenticationTokenType): String
    suspend fun getAccountId(): Long
    suspend fun getDetails(): AuthenticationDetails
}
