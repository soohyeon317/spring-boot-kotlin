package com.example.jms.endpoint.restapi.v1.account

import com.example.jms.domain.account.Account

data class AccountSignUpResponseDto(
    val email: String
) {

    constructor(account: Account): this(
        email = account.email
    )
}
