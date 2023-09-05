package com.example.securityjwt.domain.account

import org.springframework.stereotype.Repository

@Repository
interface AccountRepository {

    suspend fun save(account: Account): Account
    suspend fun findBy(email: String): Account?
}
