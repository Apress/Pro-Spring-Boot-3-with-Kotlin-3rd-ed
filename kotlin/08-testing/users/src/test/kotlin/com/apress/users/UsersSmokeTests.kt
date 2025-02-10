package com.apress.users

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.PostgreSQLContainer

@SpringBootTest
internal class UsersSmokeTests {
    @Autowired
    private lateinit var context: ApplicationContext

    @Test
    @Throws(Exception::class)
    fun contextLoads() {
        Assertions.assertThat(context).isNotNull()
    }

    @Configuration
    internal class UserTestConfiguration {
        @Bean
        @ServiceConnection
        fun postgreSQLContainer(): PostgreSQLContainer<*> =
            PostgreSQLContainer("postgres:latest")
    }
}
