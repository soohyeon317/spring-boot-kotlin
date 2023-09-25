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

    suspend fun getById(id: Long): Account? {
        return accountRepository.findById(id)
    }

    suspend fun delete(account: Account): Account {
        return accountRepository.save(
            account, true
        )
    }
}
