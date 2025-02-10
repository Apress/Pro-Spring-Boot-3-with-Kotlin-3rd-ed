package com.apress.users

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserConfiguration {
    @Bean
    fun init(userRepository: UserRepository): ApplicationListener<ApplicationReadyEvent> {
        return ApplicationListener<ApplicationReadyEvent> { applicationReadyEvent: ApplicationReadyEvent? ->
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
}
