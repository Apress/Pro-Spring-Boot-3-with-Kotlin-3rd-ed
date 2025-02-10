package com.apress.users

import io.r2dbc.spi.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.function.BiFunction


@Configuration
class UsersRoutes {
    @Autowired
    private lateinit var userRepository: UserRepository

    @get:Bean
    val usersRoute: RouterFunction<ServerResponse>
        get() = RouterFunctions.route(
            RequestPredicates.GET("/users")
        ) { _: ServerRequest ->
            ServerResponse.ok()
                .body<User, Flux<User>>(userRepository.findAll(), User::class.java)
        }

    @Bean
    fun postUserRoute(): RouterFunction<ServerResponse> {
        return RouterFunctions.route(
            RequestPredicates.POST("/users")
        ) { request: ServerRequest ->
            request
                .body<Mono<User>>(BodyExtractors.toMono(User::class.java))
                .flatMap { entity: User -> userRepository.save(entity) }
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
                    userRepository.findByEmail(request.pathVariable("email")),
                    User::class.java
                )
        }
    }

    @Bean
    fun deleteUserByEmail(): RouterFunction<ServerResponse> {
        return RouterFunctions.route(
            RequestPredicates.DELETE("/users/{email}")
        ) { request: ServerRequest ->
            userRepository.deleteByEmail(request.pathVariable("email"))
            ServerResponse.noContent().build()
        }
    }
}
