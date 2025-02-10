// Version 1
/*
package com.apress.users.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
class UserRabbitConfiguration {
    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate =
        RabbitTemplate(connectionFactory).apply {
            setExchange("USERS")
            messageConverter = Jackson2JsonMessageConverter(ObjectMapper().registerModule(JavaTimeModule()))
        }

    @Bean
    fun exchange(): TopicExchange =
        TopicExchange(USERS_EXCHANGE)

    @Bean
    fun userStatusQueue(): Queue =
        Queue(USERS_STATUS_QUEUE, true, false, false)

    @Bean
    fun userRemovedQueue(): Queue =
        Queue(USERS_REMOVED_QUEUE, true, false, false)

    @Bean
    fun userStatusBinding(): Binding =
        Binding(USERS_STATUS_QUEUE, Binding.DestinationType.QUEUE, USERS_EXCHANGE, USERS_ACTIVATED, null)

    @Bean
    fun userRemovedBinding(): Binding =
        Binding(USERS_REMOVED_QUEUE, Binding.DestinationType.QUEUE, USERS_EXCHANGE, USERS_REMOVED, null)

    companion object {
        const val USERS_EXCHANGE = "USERS"
        const val USERS_STATUS_QUEUE = "USER_STATUS"
        const val USERS_REMOVED_QUEUE = "USER_REMOVED"
        const val USERS_ACTIVATED = "users.activated"
        const val USERS_REMOVED = "users.removed"
    }
}*/

// Version 2
package com.apress.users.amqp

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class UserRabbitConfiguration {
    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate =
        RabbitTemplate(connectionFactory).apply {
            setExchange("USERS")
            messageConverter = Jackson2JsonMessageConverter(ObjectMapper().registerModule(JavaTimeModule()))
        }

    @Bean
    fun rabbitListenerContainerFactory(connectionFactory: ConnectionFactory?): SimpleRabbitListenerContainerFactory =
        SimpleRabbitListenerContainerFactory().apply {
            setConnectionFactory(connectionFactory)
            setMessageConverter(Jackson2JsonMessageConverter(ObjectMapper().registerModule(JavaTimeModule())))
        }

    companion object {
        const val USERS_EXCHANGE = "USERS"
        const val USERS_STATUS_QUEUE = "USER_STATUS"
        const val USERS_REMOVED_QUEUE = "USER_REMOVED"
        const val USERS_ACTIVATED = "users.activated"
        const val USERS_REMOVED = "users.removed"
    }
}
