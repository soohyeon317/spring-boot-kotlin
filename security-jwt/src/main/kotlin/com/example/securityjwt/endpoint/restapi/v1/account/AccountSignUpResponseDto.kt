package com.example.securityjwt.endpoint.restapi.v1.account

import com.example.securityjwt.domain.account.Account

data class AccountSignUpResponseDto(
    val email: String
) {

    constructor(account: Account): this(
        email = account.email
    )
}
