package com.apress.users.web

import com.apress.users.model.User
import com.apress.users.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.function.Function

@Configuration
class UsersRoutes {
    @Autowired
    private lateinit var userService: UserService

    @Bean
    fun usersRoute(): RouterFunction<ServerResponse> =
        RouterFunctions.route(
            RequestPredicates.GET("/users")
        ) { _: ServerRequest ->
            ServerResponse.ok()
                .body<User, Flux<User>>(userService.allUsers, User::class.java)
        }

    @Bean
    fun postUserRoute(): RouterFunction<ServerResponse> {
        return RouterFunctions.route<ServerResponse>(
            RequestPredicates.POST("/users")
        ) { request: ServerRequest ->
            request
                .body<Mono<User>>(BodyExtractors.toMono(User::class.java))
                .flatMap { user: User -> userService.saveUpdateUser(user) }
                .then(ServerResponse.ok().build())
        }
    }

    @Bean
    fun findUserByEmail(): RouterFunction<ServerResponse> {
        return RouterFunctions.route(
            RequestPredicates.GET("/users/{email}")
        ) { request: ServerRequest ->
            ServerResponse.ok()
                .body<User, Mono<User>>(
                    userService.findUserByEmail(request.pathVariable("email")),
                    User::class.java
                )
        }
    }

    @Bean
    fun deleteUserByEmail(): RouterFunction<ServerResponse> {
        return RouterFunctions.route(
            RequestPredicates.DELETE("/users/{email}")
        ) { request: ServerRequest ->
            userService.removeUserByEmail(request.pathVariable("email"))
            ServerResponse.noContent().build()
        }
    }
}