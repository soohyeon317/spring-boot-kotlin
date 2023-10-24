package com.example.securityjwt.infrastructure.persistence.jpa.authtoken

import com.example.securityjwt.domain.authtoken.AuthToken
import com.example.securityjwt.domain.authtoken.AuthTokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Repository

@Repository
class AuthTokenRepositoryImpl(
    private val springDataAuthTokenRepository: SpringDataAuthTokenRepository
) : AuthTokenRepository {

    override suspend fun save(authToken: AuthToken, willInactivate: Boolean?): AuthToken {
        val authTokenEntity = withContext(Dispatchers.IO) {
            springDataAuthTokenRepository.save(
                AuthTokenEntity(authToken, willInactivate)
            ).awaitSingle()
        }
        return authTokenEntity.toAccessToken()
    }

    override suspend fun findBy(accessToken: String): AuthToken? = withContext(Dispatchers.IO) {
        springDataAuthTokenRepository.findTopByAccessTokenAndDeletedAtIsNullOrderByIdDesc(accessToken)?.toAccessToken()
    }

    override suspend fun findBy(accountId: Long, accessToken: String): AuthToken? = withContext(Dispatchers.IO) {
        springDataAuthTokenRepository.findTopByAccountIdAndAccessTokenAndDeletedAtIsNullOrderByIdDesc(
            accountId,
            accessToken
        )?.toAccessToken()
    }

    override suspend fun findAllByAccountId(accountId: Long): List<AuthToken> {
        return withContext(Dispatchers.IO) {
            springDataAuthTokenRepository.findAllByAccountIdAndDeletedAtIsNull(accountId).map { it.toAccessToken() }
        }
    }
}
