package com.example.securityjwt.configuration.authentication

import com.example.securityjwt.exception.ErrorCode
import com.example.securityjwt.exception.UnAuthorizedException
import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthenticationManager(
    private val authenticationService: AuthenticationService
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        return mono {
            val accessToken = authentication.credentials.toString()
            if (!authenticationService.validateToken(accessToken, AuthenticationTokenType.ACCESS)) {
                throw UnAuthorizedException(ErrorCode.ACCESS_TOKEN_INVALID)
            } else if (!authenticationService.isSaved(accessToken)) {
                throw UnAuthorizedException(ErrorCode.ACCESS_TOKEN_NOT_FOUND)
            }
            accessToken
        }.flatMap {
            val authenticationToken = authenticationService.toAuthenticationToken(it)
            Mono.just(authenticationToken)
        }.flatMap {
            val auth = UsernamePasswordAuthenticationToken(it.ownerId, null, null)
            auth.details = AuthenticationDetails(
                ownerId = it.ownerId
            )
            Mono.just(auth)
        }
    }
}
