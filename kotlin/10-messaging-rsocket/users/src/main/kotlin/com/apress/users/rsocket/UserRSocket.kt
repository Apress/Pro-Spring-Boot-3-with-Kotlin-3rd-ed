package com.apress.users.rsocket

import com.apress.users.model.User
import com.apress.users.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class UserRSocket {
    @Autowired
    private lateinit var userService: UserService

    @MessageMapping("new-user")
    fun newUser(user: User): Mono<User> =
        userService.saveUpdateUser(user)

    @get:MessageMapping("all-users")
    val allUsers: Flux<User>
        get() = userService.allUsers

    @MessageMapping("user-by-email")
    fun findUserByEmail(email: String): Mono<User> =
        userService.findUserByEmail(email)

    @MessageMapping("remove-user-by-email")
    fun removeUserByEmail(email: String): Mono<Void> =
        userService.removeUserByEmail(email).let {
            Mono.empty()
        }
}
