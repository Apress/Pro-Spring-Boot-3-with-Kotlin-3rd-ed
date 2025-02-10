package com.apress.users.web.socket

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.core.MessageSendingOperations
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class UserSocket {
    @Autowired
    private lateinit var messageSendingOperations: MessageSendingOperations<String>

    fun userLogSocket(event: Map<String, Any>) {
        val map: Map<String, Any> = mapOf(
                "event" to event,
                "version" to "1.0",
                "time" to LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        )
        messageSendingOperations.convertAndSend("/topic/user-logs", map)
    }
}
