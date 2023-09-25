package com.example.securityjwt.application.account

class AccountAuthenticationCommand {

    data class SignUp(val email: String, val password: String)
    data class SignIn(val email: String, val password: String)
    data class SignInRefresh(val accessToken: String, val refreshToken: String)
    data class SignOut(val accessToken: String)
    data class Withdraw(val password: String)
}
