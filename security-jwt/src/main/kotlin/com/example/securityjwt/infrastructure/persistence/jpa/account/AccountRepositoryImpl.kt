package com.example.securityjwt.infrastructure.persistence.jpa.account

import com.example.securityjwt.domain.account.Account
import com.example.securityjwt.domain.account.AccountRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Repository

@Repository
class AccountRepositoryImpl(
    private val springDataAccountRepository: SpringDataAccountRepository
) : AccountRepository {

    override suspend fun save(account: Account, willDelete: Boolean?): Account {
        val accountEntity = springDataAccountRepository.save(
            AccountEntity(account, willDelete)
        ).awaitSingle()
        return accountEntity.toAccount()
    }

    override suspend fun findBy(email: String): Account? {
        val accountEntity = springDataAccountRepository.findByEmailAndDeletedAtIsNull(email)
            ?: return null
        return accountEntity.toAccount()
    }

    override suspend fun findById(id: Long): Account? {
        val accountEntity = springDataAccountRepository.findByIdAndDeletedAtIsNull(id)
            ?: return null
        return accountEntity.toAccount()
    }
}
