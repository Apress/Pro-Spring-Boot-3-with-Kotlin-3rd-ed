package com.apress.users.events

import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UserLogs {
    @Async
    @EventListener
    fun userActiveStatusEventHandler(event: UserActivatedEvent) {
        LOG.info("User {} active status: {}", event.email, event.active)
    }

    @Async
    @EventListener
    fun userDeletedEventHandler(event: UserRemovedEvent) {
        LOG.info("User {} DELETED at {}", event.email, event.removed)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(UserLogs::class.java)
    }
}
