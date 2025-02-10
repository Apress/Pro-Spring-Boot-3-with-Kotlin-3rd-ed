package com.apress.users.events

import com.apress.users.amqp.UserRabbitConfiguration
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UserLogs {
    @Autowired
    private lateinit var rabbitTemplate: RabbitTemplate

    @Async
    @EventListener
    fun userActiveStatusEventHandler(event: UserActivatedEvent) {
        rabbitTemplate.convertAndSend(UserRabbitConfiguration.USERS_ACTIVATED, event)
        LOG.info("User {} active status: {}", event.email, event.active)
    }

    @Async
    @EventListener
    fun userDeletedEventHandler(event: UserRemovedEvent) {
        rabbitTemplate.convertAndSend(UserRabbitConfiguration.USERS_REMOVED, event)
        LOG.info("User {} DELETED at {}", event.email, event.removed)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(UserLogs::class.java)
    }
}
