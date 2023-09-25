package com.example.securityjwt.infrastructure.persistence.jpa.authtoken

import com.example.securityjwt.domain.authtoken.AuthToken
import com.example.securityjwt.domain.authtoken.AuthTokenRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Repository

@Repository
class AuthTokenRepositoryImpl(
    private val springDataAuthTokenRepository: SpringDataAuthTokenRepository
) : AuthTokenRepository {

    override suspend fun save(authToken: AuthToken, willInactivate: Boolean?): AuthToken {
        val authTokenEntity = springDataAuthTokenRepository.save(
            AuthTokenEntity(authToken, willInactivate)
        ).awaitSingle()
        return authTokenEntity.toAccessToken()
    }

    override suspend fun findBy(accessToken: String): AuthToken? =
        springDataAuthTokenRepository.findTopByAccessTokenAndDeletedAtIsNullOrderByIdDesc(accessToken)?.toAccessToken()

    override suspend fun findBy(accountId: Long, accessToken: String): AuthToken? =
        springDataAuthTokenRepository.findTopByAccountIdAndAccessTokenAndDeletedAtIsNullOrderByIdDesc(accountId, accessToken)?.toAccessToken()

    override suspend fun findAllByAccountId(accountId: Long): List<AuthToken> {
        return springDataAuthTokenRepository.findAllByAccountIdAndDeletedAtIsNull(accountId).map { it.toAccessToken() }
    }
}
