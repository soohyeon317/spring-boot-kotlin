package com.example.securityjwt.domain.authtoken

import org.springframework.stereotype.Service

@Service
class AuthTokenDomainService(
    private val authTokenRepository: AuthTokenRepository
) {

    suspend fun create(accountId: Long, accessToken: String, refreshToken: String): AuthToken {
        return authTokenRepository.save(
            AuthToken(accountId, accessToken, refreshToken)
        )
    }

    suspend fun getBy(accessToken: String): AuthToken? {
        return authTokenRepository.findBy(accessToken)
    }

    suspend fun getBy(accountId: Long, accessToken: String): AuthToken? {
        return authTokenRepository.findBy(accessToken)
    }

    suspend fun update(authToken: AuthToken): AuthToken {
        return authTokenRepository.save(authToken)
    }

    suspend fun inactivate(authToken: AuthToken): AuthToken {
        return authTokenRepository.save(authToken, true)
    }
}
