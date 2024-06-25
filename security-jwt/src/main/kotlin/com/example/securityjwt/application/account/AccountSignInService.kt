package com.example.securityjwt.application.account

import com.example.securityjwt.configuration.authentication.authenticationTokenManager
import com.example.securityjwt.configuration.authentication.AuthenticationTokenType
import com.example.securityjwt.domain.account.AccountRepository
import com.example.securityjwt.domain.authtoken.AuthToken
import com.example.securityjwt.domain.authtoken.AuthTokenRepository
import com.example.securityjwt.exception.AccountNotFoundException
import com.example.securityjwt.exception.ErrorCode
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountSignInService(
    private val accountRepository: AccountRepository,
    private val authTokenRepository: AuthTokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationTokenManager: authenticationTokenManager
): AccountSignInUseCase {

    @Transactional(rollbackFor=[Exception::class])
    // 멀티 로그인하는 것 가능.
    override suspend fun signIn(command: AccountSignInCommand.SignIn): AuthToken {
        val account = accountRepository.findTopByEmailAndDeletedAtIsNull(command.email)
            ?: throw AccountNotFoundException(ErrorCode.EMAIL_INVALID)
        if (!passwordEncoder.matches(command.password, account.password)) {
            throw AccountNotFoundException(ErrorCode.PASSWORD_INVALID)
        }
        val accountId = account.id!!
        val accessToken = authenticationTokenManager.createToken(accountId, AuthenticationTokenType.ACCESS)
        val refreshToken = authenticationTokenManager.createToken(accountId, AuthenticationTokenType.REFRESH)
        return authTokenRepository.save(
            AuthToken(accountId, accessToken, refreshToken)
        )
    }
}
