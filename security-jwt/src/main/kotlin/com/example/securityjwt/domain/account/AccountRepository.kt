package com.example.securityjwt.domain.account

interface AccountRepository {

    suspend fun save(account: Account, willDelete: Boolean? = null): Account
    suspend fun findTopByEmailAndDeletedAtIsNull(email: String): Account?
    suspend fun findTopByIdAndDeletedAtIsNull(id: Long): Account?
}
