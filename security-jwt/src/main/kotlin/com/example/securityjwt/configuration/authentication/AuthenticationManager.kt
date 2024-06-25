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
    private val authenticationTokenManager: authenticationTokenManager
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        return mono {
            val accessToken = authentication.credentials.toString()
            val authenticationToken = authenticationTokenManager.toAuthenticationToken(accessToken)
            if (!authenticationTokenManager.isSaved(accessToken)) {
                // 만료되지 않은 토큰에 대한 강제 로그아웃 처리가 필요한 경우
                throw UnAuthorizedException(ErrorCode.ACCESS_TOKEN_NOT_FOUND)
            }
            authenticationToken
        }.flatMap {
            val auth = UsernamePasswordAuthenticationToken(it.accountId, null, null)
            auth.details = AuthenticationDetails(
                accountId = it.accountId
            )
            Mono.just(auth)
        }
    }
}
