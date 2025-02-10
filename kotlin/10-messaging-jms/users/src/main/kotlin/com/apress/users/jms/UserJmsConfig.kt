package com.apress.users.jms

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.support.converter.MappingJackson2MessageConverter
import org.springframework.jms.support.converter.MessageConverter
import org.springframework.jms.support.converter.MessageType


@Configuration
class UserJmsConfig {
    @Bean
    fun messageConverter(): MessageConverter =
        MappingJackson2MessageConverter().apply {
            setTypeIdPropertyName("_type")
            setTargetType(MessageType.TEXT)
        }

    // For Listener
    /*
    @Bean
    fun messageConverter(): MessageConverter {
        val mapper = ObjectMapper()
        mapper.registerModule(JavaTimeModule())
        val converter = MappingJackson2MessageConverter()
        converter.setTypeIdPropertyName("_type")
        converter.setTargetType(MessageType.TEXT)
        converter.setObjectMapper(mapper)
        return converter
    }
    */

    companion object {
        const val DESTINATION_ACTIVATED = "activated-users"
        const val DESTINATION_REMOVED = "removed-users"
    }
}
