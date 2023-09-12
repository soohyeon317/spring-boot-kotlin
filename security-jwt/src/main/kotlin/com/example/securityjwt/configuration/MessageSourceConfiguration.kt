package com.example.securityjwt.configuration

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import java.nio.charset.StandardCharsets

@Configuration
class MessageSourceConfiguration {

    @Bean
    fun messageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:/messages/message")
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name())
        return messageSource
    }
}