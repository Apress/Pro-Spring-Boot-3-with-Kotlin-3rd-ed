package com.apress.myretro.rsocket

import org.springframework.messaging.rsocket.service.RSocketExchange
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
interface UserClient {
    @get:RSocketExchange("all-users")
    val allUsers: Flux<User>
}
