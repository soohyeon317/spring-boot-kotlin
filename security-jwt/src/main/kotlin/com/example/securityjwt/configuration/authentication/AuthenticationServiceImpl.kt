package com.example.securityjwt.configuration.authentication

import com.example.securityjwt.domain.authtoken.AuthTokenDomainService
import com.example.securityjwt.exception.ErrorCode
import com.example.securityjwt.exception.UnAuthorizedException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl(
    private val jwtUtil: JWTUtil,
    private val authTokenDomainService: AuthTokenDomainService
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
                is IllegalArgumentException -> throw UnAuthorizedException(ErrorCode.TOKEN_INVALID)
                else -> throw e
            }
        }
    }

    override fun toAuthenticationToken(token: String): AuthenticationToken =
        AuthenticationToken(jwtUtil.parseJWTClaims(token))

    override fun isSaved(token: String): Boolean = runBlocking {
        authTokenDomainService.getBy(token) != null
    }

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
