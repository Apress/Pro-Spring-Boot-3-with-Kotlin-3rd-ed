package com.apress.users

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback
import java.util.*

@Configuration
class UserConfiguration : BeforeConvertCallback<User> {
    @Bean
    fun init(userRepository: UserRepository): ApplicationListener<ApplicationReadyEvent> {
        return ApplicationListener<ApplicationReadyEvent> { _: ApplicationReadyEvent ->
            userRepository.save(
                User(
                    email="ximena@email.com",
                    name="Ximena",
                    password="aw2s0meR!",
                    userRole = mutableListOf(UserRole.USER),
                    active = true)
            )
            userRepository.save(
                User(
                    email="norma@email.com",
                    name="Norma",
                    password="aw2s0meR!",
                    userRole = mutableListOf(UserRole.USER,UserRole.ADMIN),
                    active=true)
            )
        }
    }

    override fun onBeforeConvert(entity: User, collection: String): User {
        entity.gravatarUrl = entity.gravatarUrl ?: UserGravatar.getGravatarUrlFromEmail(entity.email!!)
        entity.userRole = entity.userRole ?: listOf(UserRole.INFO)
        return entity
    }
}
