package com.apress.myretro.amqp

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserRabbitConfiguration {
    @Bean
    fun rabbitListenerContainerFactory(connectionFactory: ConnectionFactory?): SimpleRabbitListenerContainerFactory {
        val factory = SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory)
        factory.setMessageConverter(
            Jackson2JsonMessageConverter(
                ObjectMapper()
                    .registerModule(JavaTimeModule())
            )
        )
        return factory
    }
}
