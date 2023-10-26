package com.example.securityjwt.application.account

class AccountSignInRefreshCommand {

    data class SignInRefresh(val accessToken: String, val refreshToken: String)
}
