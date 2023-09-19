package com.example.jms.application.account

class AccountAuthenticationCommand {

    data class SignUp(val email: String, val password: String)
}
