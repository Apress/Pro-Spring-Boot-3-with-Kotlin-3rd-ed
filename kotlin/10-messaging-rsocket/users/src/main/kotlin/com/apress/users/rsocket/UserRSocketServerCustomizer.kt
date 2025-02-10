package com.apress.users.rsocket

import io.rsocket.core.RSocketServer
import io.rsocket.core.Resume
import org.springframework.boot.rsocket.server.RSocketServerCustomizer

//@Component
class UserRSocketServerCustomizer : RSocketServerCustomizer {
    override fun customize(rSocketServer: RSocketServer) {
        rSocketServer.resume(Resume())
    }
}
