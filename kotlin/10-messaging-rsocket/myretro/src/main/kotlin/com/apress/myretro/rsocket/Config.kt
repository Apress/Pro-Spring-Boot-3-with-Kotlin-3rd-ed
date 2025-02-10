package com.apress.myretro.rsocket

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.service.RSocketServiceProxyFactory


@Configuration
class Config {
    @Bean
    fun getRSocketServiceProxyFactory(
        requestBuilder: RSocketRequester.Builder,
        @Value("\${myretro.users-service.host:localhost}") host: String,
        @Value("\${myretro.users-service.port:9898}") port: Int
    ): RSocketServiceProxyFactory =
        requestBuilder.tcp(host, port).let {
            RSocketServiceProxyFactory.builder(it).build()
        }

    @Bean
    fun getClient(factory: RSocketServiceProxyFactory): UserClient =
        factory.createClient(UserClient::class.java)

    @Bean
    fun commandLineRunner(userClient: UserClient): CommandLineRunner =
        CommandLineRunner { _: Array<String> ->
            userClient.allUsers.doOnNext { user: User -> LOG.info("User: {}", user) }
                .subscribe()
        }

    companion object {
        private val LOG = LoggerFactory.getLogger(Config::class.java)
    }
}
