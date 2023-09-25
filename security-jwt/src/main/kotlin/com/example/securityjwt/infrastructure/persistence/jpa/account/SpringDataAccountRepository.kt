package com.example.securityjwt.infrastructure.persistence.jpa.account

import org.springframework.data.r2dbc.repository.R2dbcRepository

interface SpringDataAccountRepository : R2dbcRepository<AccountEntity, Long> {

    suspend fun findByEmailAndDeletedAtIsNull(email: String): AccountEntity?

    suspend fun findByIdAndDeletedAtIsNull(id: Long): AccountEntity?
}
