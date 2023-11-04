package com.example.securityjwt.application.account

import com.example.securityjwt.configuration.authentication.AuthenticationService
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
    private val authenticationService: AuthenticationService
): AccountWithdrawUseCase {

    @Transactional(rollbackFor=[Exception::class])
    override suspend fun withdraw(command: AccountWithdrawCommand.Withdraw): Account {
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
