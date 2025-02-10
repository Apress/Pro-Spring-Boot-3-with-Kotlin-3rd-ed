package com.apress.users.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KConfig {
    @Bean
    fun taskTopic(): NewTopic =
        TopicBuilder.name(TOPIC)
            .partitions(1)
            .replicas(1)
            .build()

    companion object {
        const val TOPIC = "user-logs"
    }
}
