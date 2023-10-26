package com.example.securityjwt.application.account

class AccountSignOutCommand {

    data class SignOut(val accessToken: String)
}
