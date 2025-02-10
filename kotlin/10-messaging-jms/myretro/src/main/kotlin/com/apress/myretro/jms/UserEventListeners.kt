package com.apress.myretro.jms

import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.jms.annotation.JmsListeners
import org.springframework.stereotype.Component

@Component
class UserEventListeners {
    @JmsListeners(
        JmsListener(destination = "\${jms.user-events.queue.1}"),
        JmsListener(destination = "\${jms.user-events.queue.2}")
    )
    fun onUserEvent(userEvent: UserEvent?) {
        LOG.info("UserEventListeners.onUserEvent: {}", userEvent)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(UserEventListeners::class.java)
    }
}
