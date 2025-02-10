package com.apress.myretro.client

import com.apress.myretro.config.RetroBoardProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class UserClientConfig {
    @Bean
    fun webClient(retroBoardProperties: RetroBoardProperties): WebClient {
        return WebClient.builder()
            .defaultHeaders { header: HttpHeaders ->
                header.setBasicAuth(
                    retroBoardProperties.usersService!!.username!!,
                    retroBoardProperties.usersService!!.password!!
                )
            }
            .baseUrl(retroBoardProperties.usersService!!.baseUrl!!)
            .build()
    }

    @Bean
    fun userClient(webClient: WebClient?): UserClient {
        val httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(
            WebClientAdapter.create(
                webClient!!
            )
        )
            .build()
        return httpServiceProxyFactory.createClient(UserClient::class.java)
    }
}
