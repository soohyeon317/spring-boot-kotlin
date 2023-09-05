package com.example.securityjwt.configuration.authentication

import com.example.securityjwt.exception.ErrorCode
import com.example.securityjwt.exception.UnAuthorizedException
import kotlinx.coroutines.reactor.mono
import kotlinx.coroutines.runBlocking
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
        val token = authentication.credentials.toString()
        return Mono.just(authenticationService.validateToken(token, AuthenticationTokenType.ACCESS))
            .filter { valid -> valid == true }
            .switchIfEmpty(Mono.empty())
            .flatMap { Mono.just(mono {
                val authenticationToken = authenticationService.toAuthenticationToken(token)
                runBlocking {
                    if (!authenticationService.isSaved(token)) {
                        throw UnAuthorizedException(ErrorCode.ACCESS_TOKEN_NOT_FOUND)
                    }
                }
                ///////////////////////
                // (토큰 Claims 처리 로직 부분)
                ///////////////////////
                authenticationToken
            }) }
            .flatMap {
                it.flatMap { it2 ->
                    val auth = UsernamePasswordAuthenticationToken(it2.ownerId, null, null)
                    auth.details = AuthenticationDetails(
                        ownerId = it2.ownerId,
                        accessToken = token
                    )
                    Mono.just(auth)
                }
            }
    }
}
