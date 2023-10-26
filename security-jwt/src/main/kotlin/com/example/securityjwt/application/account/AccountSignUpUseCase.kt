package com.example.securityjwt.application.account

import com.example.securityjwt.domain.account.Account

fun interface AccountSignUpUseCase {

    suspend fun signUp(command: AccountSignUpCommand.SignUp): Account
}
