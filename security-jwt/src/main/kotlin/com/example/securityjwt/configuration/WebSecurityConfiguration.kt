package com.example.securityjwt.configuration

import com.example.securityjwt.configuration.authentication.AuthenticationManager
import com.example.securityjwt.exception.ErrorCode
import com.example.securityjwt.exception.UnAuthorizedException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
class WebSecurityConfiguration(
    val authenticationManager: AuthenticationManager,
    val securityContextRepository: ServerSecurityContextRepository
) {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
        http
            .csrf {
                it.disable()
            }
            .httpBasic {
                it.disable()
            }
            .formLogin {
                it.disable()
            }
            .authorizeExchange {
                it.pathMatchers(
                    // accounts
                    "/api/v1/accounts/sign-up",
                    "/api/v1/accounts/sign-in",
                    "/api/v1/accounts/sign-in/refresh",

                    // Swagger
                    "/v3/api-docs/**",
                    "/webjars/**"
                ).permitAll().anyExchange().authenticated()
            }
            .securityContextRepository(securityContextRepository)
            .authenticationManager(authenticationManager)
            .exceptionHandling {
                it.authenticationEntryPoint { _, e ->
                    Mono.error(UnAuthorizedException(ErrorCode.UNAUTHORIZED, e.message))
                }
                it.accessDeniedHandler { _, e ->
                    Mono.error(UnAuthorizedException(ErrorCode.RESOURCE_FORBIDDEN, e.message))
                }
            }
            .build()
}
