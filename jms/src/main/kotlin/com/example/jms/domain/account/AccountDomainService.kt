package com.example.jms.domain.account

import org.springframework.stereotype.Service

@Service
class AccountDomainService {

    suspend fun create(email: String, password: String): Account {
        return Account(
            email = email,
            password = password
        )
    }
}
