package com.example.jms.application.account

import com.example.jms.application.jms.JmsProducer
import com.example.jms.domain.account.Account
import com.example.jms.domain.account.AccountDomainService
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountDomainService: AccountDomainService,
    private val jmsProducer: JmsProducer
): AccountAuthenticationUseCase  {

    override suspend fun signUp(command: AccountAuthenticationCommand.SignUp): Account {
        val account = accountDomainService.create(command.email, command.password)
        kotlin.runCatching {
            sendWelcomeMessage(account.email)
        }.onFailure {
            it.printStackTrace()
        }
        return account
    }

    private suspend fun sendWelcomeMessage(to: String) {
        jmsProducer.produceWelcomeMessage(to)
    }
}
