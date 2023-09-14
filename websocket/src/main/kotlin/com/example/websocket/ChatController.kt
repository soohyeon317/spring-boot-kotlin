package com.example.websocket

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.util.HtmlUtils

@Controller
class ChatController(
    private val simpMessagingTemplate: SimpMessagingTemplate
) {

    @MessageMapping("/public-room")
    @Throws(Exception::class)
    fun publicChatting(message: PublicChatSendMessage) {
        Thread.sleep(1000) // simulated delay
        simpMessagingTemplate.convertAndSend("/sub/public-room-messages", "Hello, " + HtmlUtils.htmlEscape(message.content!!) + "!")
    }

    @MessageMapping("/private-room")
    @Throws(Exception::class)
    fun privateChatting(message: PrivateChatSendMessage) {
        Thread.sleep(1000) // simulated delay
        simpMessagingTemplate.convertAndSend("/sub/private-room/" + message.roomId, HtmlUtils.htmlEscape(message.content!!))
    }
}
