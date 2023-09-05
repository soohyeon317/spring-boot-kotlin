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

    override fun save(exchange: ServerWebExchange?, context: SecurityContext?): Mono<Void> {
        return Mono.empty()
    }

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> {
        return Mono.justOrEmpty(exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION))
            .filter { auth -> auth.startsWith(AuthenticationToken.TOKEN_GRANT_TYPE, true) }
            .flatMap { auth ->
                val token: String = auth.replace(AuthenticationToken.TOKEN_GRANT_TYPE, "", true).trim()
                val authentication: Authentication = UsernamePasswordAuthenticationToken(token, token)
                authenticationManager.authenticate(authentication).map { s -> SecurityContextImpl(s) }
            }
    }
}
