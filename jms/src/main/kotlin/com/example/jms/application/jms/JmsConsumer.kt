package com.example.jms.application.jms

import com.example.jms.domain.jms.Email
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component

@Component
class JmsConsumer {

    @JmsListener(destination = "\${jms.destination.sign-up-welcome-message}", containerFactory = "myFactory")
    fun consumeSignUpWelcomeMessage(email: Email) {
        println("Sent <$email>")
    }
}
