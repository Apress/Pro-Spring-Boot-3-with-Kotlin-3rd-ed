package com.apress.users

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback
import org.springframework.data.relational.core.sql.SqlIdentifier
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.*

@Configuration
class UserConfiguration {
    @Component
    inner class GravatarUrlGeneratingCallback : BeforeConvertCallback<User> {
        override fun onBeforeConvert(user: User, sqlIdentifier: SqlIdentifier): Mono<User> {
            return Mono.just(
                if (user.id == null && user.gravatarUrl.isNullOrEmpty())
                    user.withGravatarUrl(user.email!!)
                else user
            )
        }
    }

    @Bean
    fun init(userRepository: UserRepository): CommandLineRunner {
        return CommandLineRunner { _: Array<String> ->
            userRepository.saveAll(
                listOf(
                    User(null,
                        "ximena@email.com",
                        "Ximena",
                        null,
                        "aw2s0me",
                        listOf(UserRole.USER),
                        true),
                    User(
                        null,
                        "norma@email.com",
                        "Norma",
                        null,
                        "aw2s0me",
                        listOf(UserRole.USER, UserRole.ADMIN),
                        true
                    )
                )
            ).blockLast(Duration.ofSeconds(10))
        }
    }
}
