package com.apress.users.events

import com.apress.users.web.socket.UserSocket
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

@Component
class UserLogs {
    @Autowired
    private lateinit var userSocket: UserSocket

    @Async
    @EventListener
    fun userActiveStatusEventHandler(event: UserActivatedEvent) {
        LOG.info("User {} active status: {}", event.email, event.active)
        userSocket.userLogSocket(mapOf(
                "action" to UserActivatedEvent.name,
                "email" to event.email!!,
                "isActive" to event.active
        ) )
    }

    @Async
    @EventListener
    fun userDeletedEventHandler(event: UserRemovedEvent) {
        LOG.info("User {} DELETED at {}", event.email, event.removed)
        userSocket.userLogSocket(mapOf(
                "action" to UserRemovedEvent.name,
                "email" to event.email!!,
                "datetime" to event.removed!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        ) )
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(UserLogs::class.java)
    }
}
