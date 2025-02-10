package com.apress.users

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.validation.Validator
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.servlet.function.*

@Configuration
class UsersRoutes {
    @Bean
    fun userRoutes(usersHandler: UsersHandler): RouterFunction<ServerResponse> {
        return RouterFunctions.route().nest(RequestPredicates.path("/users")) { builder: RouterFunctions.Builder ->
            builder.GET(
                "",
                RequestPredicates.accept(MediaType.APPLICATION_JSON)
            ) { request: ServerRequest -> usersHandler.findAll(request) }
            builder.GET(
                "/{email}",
                RequestPredicates.accept(MediaType.APPLICATION_JSON)
            ) { request: ServerRequest -> usersHandler.findUserByEmail(request) }
            builder.POST("") { request: ServerRequest -> usersHandler.save(request) }
            builder.DELETE("/{email}") { request: ServerRequest -> usersHandler.deleteByEmail(request) }
        }.build()
    }

    @Bean
    fun validator(): Validator = LocalValidatorFactoryBean()
}
