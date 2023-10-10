package com.example.securityjwt.domain.account

interface AccountRepository {

    suspend fun save(account: Account, willDelete: Boolean? = null): Account
    suspend fun findBy(email: String): Account?
    suspend fun findById(id: Long): Account?
}
