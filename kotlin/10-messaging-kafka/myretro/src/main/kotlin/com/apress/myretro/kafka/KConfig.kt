package com.apress.myretro.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.support.converter.JsonMessageConverter
import org.springframework.kafka.support.converter.MessageConverter

@Configuration
class KConfig {
    @Bean
    fun messageConverter(objectMapper: ObjectMapper): MessageConverter {
        objectMapper.registerModule(JavaTimeModule())
        return JsonMessageConverter(objectMapper)
    }
}
