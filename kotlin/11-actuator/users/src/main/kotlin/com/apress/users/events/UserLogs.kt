package com.apress.users.events

import com.apress.users.actuator.LogEventEndpoint
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UserLogs {
    @Autowired
    private lateinit var logEventEndpoint: LogEventEndpoint

    @Async
    @EventListener
    fun userActiveStatusEventHandler(event: UserActivatedEvent) {
        if (logEventEndpoint.isEnable) LOG.info(
            "{} User {} active status: {} {}", logEventEndpoint.config().prefix,
            event.email, event.active, logEventEndpoint.config().postfix
        ) else LOG.info("User {} active status: {}", event.email, event.active)
    }

    @Async
    @EventListener
    fun userDeletedEventHandler(event: UserRemovedEvent) {
        if (logEventEndpoint.isEnable) LOG.info(
            "{} User {} DELETED at {} {}", logEventEndpoint.config().prefix,
            event.email, event.removed, logEventEndpoint.config().postfix
        ) else LOG.info("{} User {} DELETED at {} {}", event.email, event.removed)
    }

    @EventListener
    fun on(event: AuditApplicationEvent?) {
        LOG.info("Audit Event: {}", event)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(UserLogs::class.java)
    }
}

