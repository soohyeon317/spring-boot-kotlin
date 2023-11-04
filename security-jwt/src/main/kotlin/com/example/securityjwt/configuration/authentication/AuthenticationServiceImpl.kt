package com.example.securityjwt.configuration.authentication

import com.example.securityjwt.domain.authtoken.AuthTokenRepository
import com.example.securityjwt.exception.ErrorCode
import com.example.securityjwt.exception.UnAuthorizedException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl(
    private val jwtUtil: JWTUtil,
    private val authTokenRepository: AuthTokenRepository
) : AuthenticationService {

    override fun validateToken(token: String, tokenType: AuthenticationTokenType): Boolean {
        return try {
            jwtUtil.parseJWTClaims(token)
            true
        } catch (e: Exception) {
            when (e) {
                is ExpiredJwtException -> {
                    val errorCode = if (tokenType == AuthenticationTokenType.REFRESH) {
                        ErrorCode.REFRESH_TOKEN_EXPIRED
                    } else {
                        ErrorCode.ACCESS_TOKEN_EXPIRED
                    }
                    throw UnAuthorizedException(errorCode)
                }
                is MalformedJwtException,
                is SignatureException,
                is UnsupportedJwtException,
                is IllegalArgumentException -> {
                    val errorCode = if (tokenType == AuthenticationTokenType.REFRESH) {
                        ErrorCode.REFRESH_TOKEN_INVALID
                    } else {
                        ErrorCode.ACCESS_TOKEN_INVALID
                    }
                    throw UnAuthorizedException(errorCode)
                }
                else -> throw e
            }
        }
    }

    override fun toAuthenticationToken(accessToken: String): AuthenticationToken =
        AuthenticationToken(jwtUtil.parseJWTClaims(accessToken))

    override suspend fun isSaved(accessToken: String): Boolean = authTokenRepository.finfindTopByAccessTokenAndDeletedAtIsNullOrderByIdDescBy(accessToken) != null

    override fun createToken(ownerId: Long, tokenType: AuthenticationTokenType): String =
        jwtUtil.generate(ownerId, tokenType)

    override suspend fun getAccountId(): Long = ReactiveSecurityContextHolder
        .getContext()
        .map { security: SecurityContext -> security.authentication.principal.toString().toLong() }
        .doOnError {
            throw UnAuthorizedException(ErrorCode.UNAUTHORIZED, it.message)
        }.awaitSingle()

    override suspend fun getDetails(): AuthenticationDetails = ReactiveSecurityContextHolder
        .getContext()
        .map { security: SecurityContext -> security.authentication.details as AuthenticationDetails }
        .doOnError {
            throw UnAuthorizedException(ErrorCode.UNAUTHORIZED, it.message)
        }.awaitSingle()
}
