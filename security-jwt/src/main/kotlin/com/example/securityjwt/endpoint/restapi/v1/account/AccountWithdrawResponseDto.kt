package com.example.securityjwt.endpoint.restapi.v1.account

import com.example.securityjwt.domain.account.Account
import kotlinx.serialization.Serializable

@Serializable
data class AccountWithdrawResponseDto(
    val email: String
) {

    constructor(account: Account): this(
        email = account.email
    )
}
