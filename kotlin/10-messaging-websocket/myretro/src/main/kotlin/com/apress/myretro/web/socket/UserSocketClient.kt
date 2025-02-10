package com.apress.myretro.web.socket

import org.slf4j.LoggerFactory
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.stereotype.Component

@Component
class UserSocketClient : StompSessionHandlerAdapter() {
    override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
        LOG.info("Client connected: headers {}", connectedHeaders)
        session.subscribe(TOPIC, this)
    }

    override fun handleFrame(headers: StompHeaders, payload: Any?) {
        LOG.info("Client received: payload {}, headers {}", payload, headers)
    }

    override fun handleException(
        session: StompSession, command: StompCommand?,
        headers: StompHeaders, payload: ByteArray, exception: Throwable
    ) {
        LOG.error(
            "Client error: exception {}, command {}, payload {}, headers {}",
            exception.message, command, payload, headers
        )
    }

    override fun handleTransportError(session: StompSession, exception: Throwable) {
        LOG.error("Client transport error: error {}", exception.message)
    }

    companion object {
        private const val TOPIC = "/topic/user-logs"
        private val LOG = LoggerFactory.getLogger(UserSocketClient::class.java)
    }
}
