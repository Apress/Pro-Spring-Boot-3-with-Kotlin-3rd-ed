package com.apress.users.events

import com.apress.users.kafka.KConfig
import com.apress.users.kafka.KUserLogs
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UserLogs {
    @Autowired
    private lateinit var kUserLogs: KUserLogs

    @Async
    @EventListener
    fun userActiveStatusEventHandler(event: UserActivatedEvent) {
        LOG.info("User {} active status: {}", event.email, event.active)
        kUserLogs.send(
            KConfig.TOPIC,
            event.email!!,
            UserEvent("STATUS", event.email, event.active!!, LocalDateTime.now())
        )
    }

    @Async
    @EventListener
    fun userDeletedEventHandler(event: UserRemovedEvent) {
        LOG.info("User {} DELETED at {}", event.email!!, event.removed!!)
        kUserLogs.send(
            KConfig.TOPIC,
            event.email!!,
            UserEvent("DELETED", event.email, false, LocalDateTime.now())
        )
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(UserLogs::class.java)
    }
}
