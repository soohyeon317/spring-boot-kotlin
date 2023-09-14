package com.example.websocket

data class PrivateChatSendMessage(
    var roomId: String? = null,
    var content: String? = null
)
