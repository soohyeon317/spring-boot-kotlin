package com.example.securityjwt.infrastructure.persistence.jpa.account

import com.example.securityjwt.domain.account.Account
import com.example.securityjwt.domain.account.AccountRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Repository

@Repository
class AccountRepositoryImpl(
    private val springDataAccountRepository: SpringDataAccountRepository
) : AccountRepository {

    override suspend fun save(account: Account): Account {
        val accountEntity = AccountEntity(account)
        val savedAccountEntity = springDataAccountRepository.save(
            accountEntity
        ).awaitSingle()
        return savedAccountEntity.toAccount()
    }

    override suspend fun findBy(email: String): Account? {
        val accountEntity = springDataAccountRepository.findByEmail(email)
            ?: return null
        return accountEntity.toAccount()
    }
}
