package com.apress.myretro

import org.springframework.boot.SpringApplication
import org.springframework.boot.devtools.restart.RestartScope
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.MongoDBContainer

@Configuration
class RetroBoardDevMain {
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
