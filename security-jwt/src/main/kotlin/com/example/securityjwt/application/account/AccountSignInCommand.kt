package com.example.securityjwt.application.account

class AccountSignInCommand {

    data class SignIn(val email: String, val password: String)
}
