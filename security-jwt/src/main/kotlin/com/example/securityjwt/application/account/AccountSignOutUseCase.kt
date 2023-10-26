package com.example.securityjwt.application.account

import com.example.securityjwt.domain.authtoken.AuthToken

fun interface AccountSignOutUseCase {

    suspend fun signOut(command: AccountSignOutCommand.SignOut): AuthToken
}
