package com.apress.users.jms

import com.apress.users.events.UserActivatedEvent
import jakarta.jms.JMSConnectionFactoryDefinition
import jakarta.jms.JMSException
import jakarta.jms.JMSSessionMode
import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component

@Component
class UserEventListeners {
    @JmsListener(destination = UserJmsConfig.Companion.DESTINATION_ACTIVATED)
    fun onActivatedUserEvent(event: UserActivatedEvent) {
        LOG.info("JMS User {}", event)
    }

    // Generic ActiveMQMessage > jakarta.jms.Message
    @JmsListener(destination = UserJmsConfig.Companion.DESTINATION_REMOVED)
    @Throws(JMSException::class)
    fun onRemovedUserEvent(event: Any?) {
        LOG.info("JMS User DELETED message: {} ", event)
    }

    // Is necessary add the ObjectMapper to convert the message to the UserRemovedEvent
    /*
    @JmsListener(destination = UserJmsConfig.DESTINATION_REMOVED)
    fun onRemovedUserEvent(event:UserRemovedEvent) {
        LOG.info("JMS User DELETED message: {} ", event)
    }
    */

    companion object {
        private val LOG = LoggerFactory.getLogger(UserEventListeners::class.java)
    }
}
