package com.apress.myretro.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class UserClientConfig {
    @Bean
    fun webClient(
        @Value("\${users.app.url}") baseUrl: String,
        @Value("\${users.app.username}") username: String,
        @Value("\${users.app.password}") password: String
    ): WebClient {
        return WebClient.builder()
            .defaultHeaders { header: HttpHeaders ->
                header.setBasicAuth(
                    username, password
                )
            }
            .baseUrl(baseUrl)
            .build()
    }

    @Bean
    fun userClient(webClient: WebClient): UserClient {
        val httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(
            WebClientAdapter.create(
                webClient
            )
        ).build()
        return httpServiceProxyFactory.createClient(UserClient::class.java)
    }
}
