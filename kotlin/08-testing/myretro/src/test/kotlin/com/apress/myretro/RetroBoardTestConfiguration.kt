package com.apress.myretro

import org.springframework.boot.SpringApplication
import org.springframework.boot.devtools.restart.RestartScope
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.MongoDBContainer

@Profile("!mongoTest")
@Configuration
class RetroBoardTestConfiguration {
    @Bean
    @RestartScope
    @ServiceConnection
    fun mongoDBContainer(): MongoDBContainer {
        return MongoDBContainer("mongo:latest")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.from { obj: Array<String> -> MyretroApplication.main(obj) }.run(*args)
        }
    }
}
