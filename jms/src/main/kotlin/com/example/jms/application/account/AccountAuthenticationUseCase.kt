package com.example.jms.application.account

import com.example.jms.domain.account.Account

interface AccountAuthenticationUseCase {

    suspend fun signUp(command: AccountAuthenticationCommand.SignUp): Account
}
