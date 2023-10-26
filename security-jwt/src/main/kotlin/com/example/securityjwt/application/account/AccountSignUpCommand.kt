package com.example.securityjwt.application.account

class AccountSignUpCommand {

    data class SignUp(val email: String, val password: String)
}
