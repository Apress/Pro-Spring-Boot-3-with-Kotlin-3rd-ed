package com.apress.users.config

import com.apress.users.model.User
import com.apress.users.model.UserGravatar.getGravatarUrlFromEmail
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback
import org.springframework.data.relational.core.sql.SqlIdentifier
import reactor.core.publisher.Mono
import java.util.*

@Configuration
class UserConfiguration {
    @Bean
    fun idGeneratingCallback(): BeforeConvertCallback<User> {
        return BeforeConvertCallback<User> { user: User, _: SqlIdentifier? ->
            if (user.id == null && user.gravatarUrl.isNullOrEmpty()) {
                return@BeforeConvertCallback Mono.just<User>(
                    User(
                        UUID.randomUUID(), user.email, user.name,
                        getGravatarUrlFromEmail(user.email!!),
                        user.password, user.userRole, user.active
                    )
                )
            }
            Mono.just(user)
        }
    }
}
