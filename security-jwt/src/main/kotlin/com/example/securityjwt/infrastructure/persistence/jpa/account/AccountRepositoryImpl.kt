package com.example.securityjwt.infrastructure.persistence.jpa.account

import com.example.securityjwt.domain.account.Account
import com.example.securityjwt.domain.account.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Repository

@Repository
class AccountRepositoryImpl(
    private val springDataAccountRepository: SpringDataAccountRepository,
    private val ioDispatcher: CoroutineDispatcher
) : AccountRepository {

    override suspend fun save(account: Account, willDelete: Boolean?): Account = withContext(ioDispatcher) {
        springDataAccountRepository.save(
            AccountEntity(account, willDelete)
        ).awaitSingle().toAccount()
    }

    override suspend fun findBy(email: String): Account? = withContext(ioDispatcher) {
        springDataAccountRepository.findTopByEmailAndDeletedAtIsNull(email).awaitSingleOrNull()?.toAccount()
    }


    override suspend fun findById(id: Long): Account? = withContext(ioDispatcher) {
        springDataAccountRepository.findTopByIdAndDeletedAtIsNull(id).awaitSingleOrNull()?.toAccount()
    }
}
