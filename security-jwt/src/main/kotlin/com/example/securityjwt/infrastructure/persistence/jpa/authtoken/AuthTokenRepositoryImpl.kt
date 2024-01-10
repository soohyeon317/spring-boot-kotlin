package com.example.securityjwt.infrastructure.persistence.jpa.authtoken

import com.example.securityjwt.domain.authtoken.AuthToken
import com.example.securityjwt.domain.authtoken.AuthTokenRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Repository

@Repository
class AuthTokenRepositoryImpl(
    private val springDataAuthTokenRepository: SpringDataAuthTokenRepository,
    private val ioDispatcher: CoroutineDispatcher
) : AuthTokenRepository {

    override suspend fun save(authToken: AuthToken, willInactivate: Boolean?): AuthToken = withContext(ioDispatcher) {
        springDataAuthTokenRepository.save(
            AuthTokenEntity(authToken, willInactivate)
        ).awaitSingle().toAccessToken()
    }

    override suspend fun findTopByAccessTokenAndDeletedAtIsNullOrderByIdDesc(accessToken: String): AuthToken? = withContext(ioDispatcher) {
        springDataAuthTokenRepository.findTopByAccessTokenAndDeletedAtIsNullOrderByIdDesc(accessToken)
            .awaitSingleOrNull()?.toAccessToken()
    }

    override suspend fun findTopByAccountIdAndAccessTokenAndDeletedAtIsNullOrderByIdDesc(accountId: Long, accessToken: String): AuthToken? = withContext(ioDispatcher) {
        springDataAuthTokenRepository.findTopByAccountIdAndAccessTokenAndDeletedAtIsNullOrderByIdDesc(
            accountId,
            accessToken
        ).awaitSingleOrNull()?.toAccessToken()
    }

    override suspend fun findAllByAccountIdAndDeletedAtIsNull(accountId: Long): List<AuthToken> = withContext(ioDispatcher) {
        springDataAuthTokenRepository.findAllByAccountIdAndDeletedAtIsNull(accountId)
            .collectList().awaitSingle()
            .flatMap {
                listOf(it.toAccessToken())
            }
    }
}
