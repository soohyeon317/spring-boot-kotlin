package com.example.securityjwt.application.account

import com.example.securityjwt.domain.authtoken.AuthToken

fun interface AccountSignInUseCase {

    suspend fun signIn(command: AccountSignInCommand.SignIn): AuthToken
}
