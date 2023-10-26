package com.example.securityjwt.application.account

import com.example.securityjwt.domain.authtoken.AuthToken

fun interface AccountSignInRefreshUseCase {

    suspend fun refreshSignIn(command: AccountSignInRefreshCommand.SignInRefresh): AuthToken
}
