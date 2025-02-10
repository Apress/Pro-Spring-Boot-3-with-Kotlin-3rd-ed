package com.apress.users.events

import com.apress.users.jms.UserJmsConfig
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.jms.core.JmsTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UserLogs {
    @Autowired
    private lateinit var jmsTemplate: JmsTemplate

    @Async
    @EventListener
    fun userActiveStatusEventHandler(event: UserActivatedEvent) {
        jmsTemplate.convertAndSend(UserJmsConfig.DESTINATION_ACTIVATED, event)
        LOG.info("User {} active status: {}", event.email, event.active)
    }

    @Async
    @EventListener
    fun userDeletedEventHandler(event: UserRemovedEvent) {
        jmsTemplate.convertAndSend(UserJmsConfig.DESTINATION_REMOVED, event)
        LOG.info("User {} DELETED at {}", event.email, event.removed)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(UserLogs::class.java)
    }
}
