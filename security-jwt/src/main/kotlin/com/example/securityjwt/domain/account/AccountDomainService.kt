package com.example.securityjwt.domain.account

import org.springframework.stereotype.Service

@Service
class AccountDomainService(
    private val accountRepository: AccountRepository
) {

    suspend fun create(email: String, password: String): Account {
        return accountRepository.save(
            Account(email, password)
        )
    }

    suspend fun getBy(email: String): Account? {
        return accountRepository.findBy(email)
    }
}
