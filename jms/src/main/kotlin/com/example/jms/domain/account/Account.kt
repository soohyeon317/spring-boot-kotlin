package com.example.jms.domain.account

import java.time.LocalDateTime

data class Account (
    val id: Long?,
    val email: String,
    val password: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val deletedAt: LocalDateTime?
) {

    constructor(email: String, password: String): this(
        id = null,
        email = email,
        password = password,
        createdAt = null,
        updatedAt = null,
        deletedAt = null
    )
}
