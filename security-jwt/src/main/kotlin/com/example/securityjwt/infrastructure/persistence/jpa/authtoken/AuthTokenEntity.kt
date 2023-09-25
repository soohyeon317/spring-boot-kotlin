package com.example.securityjwt.infrastructure.persistence.jpa.authtoken

import com.example.securityjwt.domain.authtoken.AuthToken
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("auth_token")
data class AuthTokenEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    val accountId: Long,
    val accessToken: String,
    val refreshToken: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val deletedAt: LocalDateTime?
) {

    constructor(authToken: AuthToken, willInactivate: Boolean? = null) :
            this(
                id = authToken.id,
                accountId = authToken.accountId,
                accessToken = authToken.accessToken,
                refreshToken = authToken.refreshToken,
                createdAt = if (authToken.id == null) {
                    LocalDateTime.now()
                } else {
                    authToken.createdAt ?: LocalDateTime.now()
                },
                updatedAt = if (authToken.id != null) {
                    LocalDateTime.now()
                } else {
                    null
                },
                deletedAt = if (willInactivate != null && willInactivate == true) {
                    LocalDateTime.now()
                } else {
                    null
                }
            )

    fun toAccessToken(): AuthToken = AuthToken(
        id = this.id,
        accountId = this.accountId,
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        createdAt = this.createdAt
    )
}
