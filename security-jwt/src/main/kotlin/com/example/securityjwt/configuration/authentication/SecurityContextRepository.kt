package com.example.securityjwt.configuration.authentication

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class SecurityContextRepository(
    val authenticationManager: AuthenticationManager
) : ServerSecurityContextRepository {

    val authDelimiter = " "

    override fun save(exchange: ServerWebExchange?, context: SecurityContext?): Mono<Void> {
        return Mono.empty()
    }

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> {
        val authorizationHeaderPrefix = AuthenticationToken.BEARER_TOKEN_AUTH_TYPE.plus(authDelimiter)
        return Mono.justOrEmpty(exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION))
            .filter { auth -> auth.startsWith(authorizationHeaderPrefix, true) }
            .flatMap { auth ->
                val accessToken: String = auth.replace(authorizationHeaderPrefix, "", true).trim()
                val authentication: Authentication = UsernamePasswordAuthenticationToken(accessToken, accessToken)
                authenticationManager.authenticate(authentication).map { s -> SecurityContextImpl(s) }
            }
    }
}
