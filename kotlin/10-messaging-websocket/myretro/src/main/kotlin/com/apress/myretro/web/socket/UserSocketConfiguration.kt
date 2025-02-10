package com.apress.myretro.web.socket

import com.apress.myretro.config.RetroBoardProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport
import org.springframework.web.socket.sockjs.client.SockJsClient
import org.springframework.web.socket.sockjs.client.Transport
import org.springframework.web.socket.sockjs.client.WebSocketTransport

@Configuration
class UserSocketConfiguration {
    @Autowired
    private lateinit var retroBoardProperties: RetroBoardProperties

    @Bean
    fun webSocketStompClient(
        webSocketClient: WebSocketClient, userSocketMessageConverter: UserSocketMessageConverter,
        userSocketClient: StompSessionHandler
    ): WebSocketStompClient {
        val webSocketStompClient = WebSocketStompClient(webSocketClient)
        webSocketStompClient.messageConverter = userSocketMessageConverter
//        webSocketStompClient.connect(
//            retroBoardProperties.usersService!!.hostname + retroBoardProperties.usersService!!.basePath,
//            userSocketClient
//        )
        webSocketStompClient.connectAsync(
            retroBoardProperties.usersService!!.hostname + retroBoardProperties.usersService!!.basePath,
            userSocketClient
        )
        return webSocketStompClient
    }

    @Bean
    fun webSocketClient(): WebSocketClient {
        val transports: MutableList<Transport> = mutableListOf(
            WebSocketTransport(StandardWebSocketClient()),
            RestTemplateXhrTransport()
        )
        return SockJsClient(transports)
    }
}
