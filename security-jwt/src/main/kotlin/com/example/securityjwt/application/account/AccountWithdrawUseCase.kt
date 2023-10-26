package com.example.securityjwt.application.account

import com.example.securityjwt.domain.account.Account

fun interface AccountWithdrawUseCase {

    suspend fun withdraw(command: AccountWithdrawCommand.Withdraw): Account
}
