package com.example.securityjwt.infrastructure.persistence.jpa.account

import com.example.securityjwt.domain.account.Account
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("account")
data class AccountEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    val email: String,
    val password: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val deletedAt: LocalDateTime?
) {

    constructor(account: Account, willDelete: Boolean? = null) :
            this(
                id = account.id,
                email = account.email,
                password = account.password,
                createdAt = if (account.id == null) {
                    LocalDateTime.now()
                } else {
                    account.createdAt ?: LocalDateTime.now()
                },
                updatedAt = if (account.id != null) {
                    if (willDelete == true) {
                        account.updatedAt
                    } else {
                        LocalDateTime.now()
                    }
                } else {
                    null
                },
                deletedAt = if (willDelete != null && willDelete == true) {
                    LocalDateTime.now()
                } else {
                    null
                }
            )

    fun toAccount(): Account = Account(
        id = this.id,
        email = this.email,
        password = this.password,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        deletedAt = this.deletedAt
    )
}
