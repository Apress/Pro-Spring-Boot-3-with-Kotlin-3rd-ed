package com.apress.myretro.amqp

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class UserListener {
    @RabbitListener(
        bindings = [QueueBinding(
            value = Queue(
                name = USERS_ALL_QUEUE,
                durable = "true",
                autoDelete = "false"
            ), exchange = Exchange(name = USERS_EXCHANGE, type = "topic"), key = [USERS_ALL]
        )]
    )
    fun userStatusEventProcessing(userEvent: UserEvent?) {
        LOG.info("[AMQP - Event] Received: {}", userEvent)
    }

    companion object {
        private const val USERS_ALL = "users.*"
        private const val USERS_ALL_QUEUE = "USER_ALL"
        private const val USERS_EXCHANGE = "USERS"
        private val LOG = LoggerFactory.getLogger(UserListener::class.java)
    }
}
