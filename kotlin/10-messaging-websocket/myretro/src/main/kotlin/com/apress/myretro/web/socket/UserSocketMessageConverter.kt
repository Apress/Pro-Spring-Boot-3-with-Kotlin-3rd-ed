package com.apress.myretro.web.socket

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.slf4j.LoggerFactory
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.converter.MessageConverter
import org.springframework.stereotype.Component

@Component
class UserSocketMessageConverter : MessageConverter {
    override fun fromMessage(message: Message<*>, targetClass: Class<*>): Any? {
        val mapper: ObjectMapper = ObjectMapper().registerModule(JavaTimeModule())
        var userEvent: Event? = null
        try {
            val m = String((message.payload as ByteArray))
            userEvent = mapper.readValue(m, Event::class.java)
        } catch (ex: Exception) {
            LOG.error("Cannot Deserialize - {}", ex.message)
        }
        return userEvent
    }

    override fun toMessage(payload: Any, headers: MessageHeaders?): Message<*> {
        throw UnsupportedOperationException()
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(UserSocketMessageConverter::class.java)
    }
}
