package com.example.securityjwt.application.account

import com.example.securityjwt.configuration.authentication.authenticationTokenManager
import com.example.securityjwt.domain.account.Account
import com.example.securityjwt.domain.account.AccountRepository
import com.example.securityjwt.domain.authtoken.AuthTokenRepository
import com.example.securityjwt.exception.AccountNotFoundException
import com.example.securityjwt.exception.ErrorCode
import com.example.securityjwt.exception.RequestParameterInvalidException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountWithdrawService(
    private val accountRepository: AccountRepository,
    private val authTokenRepository: AuthTokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationTokenManager: authenticationTokenManager
): AccountWithdrawUseCase {

    @Transactional(rollbackFor=[Exception::class])
    override suspend fun withdraw(command: AccountWithdrawCommand.Withdraw): Account {
        val accountId = authenticationTokenManager.getAccountId()
        val account = accountRepository.findTopByIdAndDeletedAtIsNull(accountId)
            ?: throw AccountNotFoundException(ErrorCode.ACCESS_TOKEN_INVALID)
        if (!passwordEncoder.matches(command.password, account.password)) {
            throw RequestParameterInvalidException(ErrorCode.PASSWORD_INVALID)
        }
        deleteAuthTokens(accountId)
        return accountRepository.save(
            account, true
        )
    }

    private suspend fun deleteAuthTokens(accountId: Long) {
        val authTokenList = authTokenRepository.findAllByAccountIdAndDeletedAtIsNull(accountId)
        for (authToken in authTokenList) {
            authTokenRepository.save(authToken, true)
        }
    }
}
