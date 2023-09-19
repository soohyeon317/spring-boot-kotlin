package com.example.jms.application.jms

import com.example.jms.domain.jms.Email
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Service

@Service
class JmsProducer(
    private val jmsTemplate: JmsTemplate
) {

    @Value("\${jms.destination.sign-up-welcome-message}")
    lateinit var signUpWelcomeMessageDestination: String

    suspend fun produceWelcomeMessage(to: String) {
        jmsTemplate.convertAndSend(signUpWelcomeMessageDestination,
            Email(to, "We welcome you.", "Thank you for joining us."))
    }
}
