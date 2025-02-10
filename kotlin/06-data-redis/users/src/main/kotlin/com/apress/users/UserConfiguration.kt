package com.apress.users

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserConfiguration {
    @Bean
    fun init(userRepository: UserRepository): ApplicationListener<ApplicationReadyEvent> {
        return ApplicationListener<ApplicationReadyEvent> { _: ApplicationReadyEvent ->
            userRepository.save(
                User(
                    email="ximena@email.com",
                    name="Ximena",
                    gravatarUrl="https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar",
                    password="aw2s0meR!",
                    userRole = mutableListOf(UserRole.USER),
                    active = true)
            )
            userRepository.save(
                User(
                    email="norma@email.com",
                    name="Norma",
                    gravatarUrl="https://www.gravatar.com/avatar/f07f7e553264c9710105edebe6c465e7?d=wavatar",
                    password="aw2s0meR!",
                    userRole = mutableListOf(UserRole.USER,UserRole.ADMIN),
                    active=true)
            )
        }
    }
}
