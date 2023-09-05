package com.example.securityjwt.configuration.authentication

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
        val token = authentication.credentials.toString()
        return Mono.just(authenticationService.validateToken(token, AuthenticationTokenType.ACCESS))
            .filter { valid -> valid == true }
            .switchIfEmpty(Mono.empty())
            .flatMap { Mono.just(mono {
                val authenticationToken = authenticationService.toAuthenticationToken(token)
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
