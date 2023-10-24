package com.example.securityjwt.infrastructure.persistence.jpa.account

import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono

interface SpringDataAccountRepository : R2dbcRepository<AccountEntity, Long> {

    fun findTopByEmailAndDeletedAtIsNull(email: String): Mono<AccountEntity>

    fun findTopByIdAndDeletedAtIsNull(id: Long): Mono<AccountEntity>
}
