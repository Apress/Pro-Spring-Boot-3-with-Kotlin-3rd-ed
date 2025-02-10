package com.apress.users

import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.testcontainers.containers.PostgreSQLContainer

@Configuration
@Profile("!mockBean & !spyBean & !mockMvc & !integration")
class UserTestConfiguration {
    @Bean
    @ServiceConnection
    fun postgreSQLContainer(): PostgreSQLContainer<*> {
        return PostgreSQLContainer("postgres:latest")
    }
}
