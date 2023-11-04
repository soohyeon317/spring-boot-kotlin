package com.example.securityjwt.application.account

import com.example.securityjwt.domain.account.Account
import com.example.securityjwt.domain.account.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountSignUpService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val ioDispatcher: CoroutineDispatcher
): AccountSignUpUseCase {

    @Transactional(rollbackFor=[Exception::class])
    override suspend fun signUp(command: AccountSignUpCommand.SignUp): Account = withContext(ioDispatcher) {
        val encodedPassword = passwordEncoder.encode(command.password)
        accountRepository.save(
            Account(command.email, encodedPassword)
        )
    }
}
