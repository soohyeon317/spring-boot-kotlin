package com.example.securityjwt.application.account

import com.example.securityjwt.domain.authtoken.AuthToken
import com.example.securityjwt.domain.account.Account

interface AccountAuthenticationUseCase {

    suspend fun signUp(command: AccountAuthenticationCommand.SignUp): Account
    suspend fun signIn(command: AccountAuthenticationCommand.SignIn): AuthToken
    suspend fun refreshSignIn(command: AccountAuthenticationCommand.SignInRefresh): AuthToken
    suspend fun signOut(command: AccountAuthenticationCommand.SignOut): AuthToken
}
