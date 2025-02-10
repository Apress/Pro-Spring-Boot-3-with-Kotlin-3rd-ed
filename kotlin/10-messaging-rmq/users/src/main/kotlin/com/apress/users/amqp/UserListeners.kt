package com.apress.users.amqp

import com.apress.users.events.UserActivatedEvent
import com.apress.users.events.UserRemovedEvent
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class UserListeners {
    @RabbitListener(
        bindings = [QueueBinding(
            value = Queue(
                name = UserRabbitConfiguration.USERS_STATUS_QUEUE,
                durable = "true",
                autoDelete = "false"
            ),
            exchange = Exchange(name = UserRabbitConfiguration.USERS_EXCHANGE, type = "topic"),
            key = arrayOf(UserRabbitConfiguration.USERS_ACTIVATED)
        )]
    )
    fun userStatusEventProcessing(activatedEvent: UserActivatedEvent) {
        LOG.info("[AMQP - Event] Activated Event Received: {}", activatedEvent)
    }

    @RabbitListener(
        bindings = [QueueBinding(
            value = Queue(
                name = UserRabbitConfiguration.USERS_REMOVED_QUEUE,
                durable = "true",
                autoDelete = "false"
            ),
            exchange = Exchange(name = UserRabbitConfiguration.USERS_EXCHANGE, type = "topic"),
            key = arrayOf(UserRabbitConfiguration.USERS_REMOVED)
        )]
    )
    fun userRemovedEventProcessing(removedEvent: UserRemovedEvent) {
        LOG.info("[AMQP - Event] Activated Event Received: {}", removedEvent)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(UserListeners::class.java)
    }
}
