package com.apress.myretro.jms

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import jakarta.jms.JMSException
import jakarta.jms.Message
import jakarta.jms.Session
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.support.converter.MessageConversionException
import org.springframework.jms.support.converter.MessageConverter

@Configuration
class UserEventConfig {
    @Bean
    fun messageConverter(): MessageConverter {
        return object : MessageConverter {
            @Throws(JMSException::class, MessageConversionException::class)
            override fun toMessage(`object`: Any, session: Session): Message {
                throw UnsupportedOperationException("Not supported yet.")
            }

            @Throws(JMSException::class, MessageConversionException::class)
            override fun fromMessage(message: Message): Any {
                val mapper = ObjectMapper()
                mapper.registerModule(JavaTimeModule())
                val type = message.getStringProperty("_type")
                return mapper
                    .readValue(message.getBody(String::class.java), UserEvent::class.java)
                    .apply {
                        action = if (type.contains("Removed")) "Removed" else "Activated"
                    }
            }
        }
    }
}
