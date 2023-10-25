package com.example.securityjwt.application.account

import com.example.securityjwt.configuration.authentication.AuthenticationService
import com.example.securityjwt.configuration.authentication.AuthenticationTokenType
import com.example.securityjwt.domain.account.Account
import com.example.securityjwt.domain.account.AccountRepository
import com.example.securityjwt.domain.authtoken.AuthToken
import com.example.securityjwt.domain.authtoken.AuthTokenRepository
import com.example.securityjwt.exception.AccountNotFoundException
import com.example.securityjwt.exception.AuthTokenNotFoundException
import com.example.securityjwt.exception.ErrorCode
import com.example.securityjwt.exception.RequestParameterInvalidException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val authTokenRepository: AuthTokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationService: AuthenticationService,
    private val ioDispatcher: CoroutineDispatcher
): AccountAuthenticationUseCase  {

    override suspend fun signUp(command: AccountAuthenticationCommand.SignUp): Account = withContext(ioDispatcher) {
        val encodedPassword = passwordEncoder.encode(command.password)
        accountRepository.save(
            Account(command.email, encodedPassword)
        )
    }

    // 멀티 로그인하는 것 가능.
    override suspend fun signIn(command: AccountAuthenticationCommand.SignIn): AuthToken {
        val account = accountRepository.findBy(command.email)
            ?: throw AccountNotFoundException(ErrorCode.EMAIL_INVALID)
        if (!passwordEncoder.matches(command.password, account.password)) {
            throw AccountNotFoundException(ErrorCode.PASSWORD_INVALID)
        }
        val accountId = account.id!!
        val accessToken = authenticationService.createToken(accountId, AuthenticationTokenType.ACCESS)
        val refreshToken = authenticationService.createToken(accountId, AuthenticationTokenType.REFRESH)
        return authTokenRepository.save(
            AuthToken(accountId, accessToken, refreshToken)
        )
    }

    override suspend fun refreshSignIn(command: AccountAuthenticationCommand.SignInRefresh): AuthToken {
        // RefreshToken 유효성 검증
        authenticationService.validateToken(command.refreshToken, AuthenticationTokenType.REFRESH)

        // 저장소로부터 해당 AccessToken 정보와 일치하는 AuthToken 데이터 가져오기
        val authToken = authTokenRepository.findBy(command.accessToken)
            ?: throw AuthTokenNotFoundException(ErrorCode.ACCESS_TOKEN_NOT_FOUND)

        // RefreshToken 일치 여부 검사
        if (command.refreshToken != authToken.refreshToken) {
            throw RequestParameterInvalidException(ErrorCode.REFRESH_TOKEN_NOT_MATCHED)
        }

        val newAccessToken = authenticationService.createToken(authToken.accountId, AuthenticationTokenType.ACCESS)
        val newRefreshToken = authenticationService.createToken(authToken.accountId, AuthenticationTokenType.REFRESH)
        val updatedAuthToken = authToken.update(newAccessToken, newRefreshToken)
        return authTokenRepository.save(updatedAuthToken)
    }

    // 현재 로그인 계정을 로그아웃할 수도 있고, 현재 로그인 계정과 관련된 AccessToken을 지정하여 로그아웃 시키는 것도 허용.
    override suspend fun signOut(command: AccountAuthenticationCommand.SignOut): AuthToken {
        val accountId = authenticationService.getAccountId()
        val selectedAccessToken = command.accessToken
        val authToken = authTokenRepository.findBy(accountId, selectedAccessToken)
            ?: throw AuthTokenNotFoundException(ErrorCode.ACCESS_TOKEN_NOT_FOUND)
        return authTokenRepository.save(authToken, true)
    }

    override suspend fun withdraw(command: AccountAuthenticationCommand.Withdraw): Account {
        val accountId = authenticationService.getAccountId()
        val account = accountRepository.findById(accountId)
            ?: throw AccountNotFoundException(ErrorCode.ACCESS_TOKEN_INVALID)
        if (!passwordEncoder.matches(command.password, account.password)) {
            throw RequestParameterInvalidException(ErrorCode.PASSWORD_INVALID)
        }
        inactivateAuthTokens(accountId)
        return accountRepository.save(
            account, true
        )
    }

    private suspend fun inactivateAuthTokens(accountId: Long) {
        val authTokenList = authTokenRepository.findAllByAccountId(accountId)
        for (authToken in authTokenList) {
            authTokenRepository.save(authToken, true)
        }
    }
}
