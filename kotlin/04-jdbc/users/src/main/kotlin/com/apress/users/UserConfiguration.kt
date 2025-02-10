package com.apress.users

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserConfiguration {
    @Bean
    fun init(userRepository: SimpleRepository<User, Int>): ApplicationListener<ApplicationReadyEvent> =
        ApplicationListener<ApplicationReadyEvent> { _: ApplicationReadyEvent ->
            val ximena: User = User(
                email = "ximena@email.com",
                name = "Ximena",
                password = "aw2s0meR!",
                active = true,
                userRole = mutableListOf(UserRole.USER))
            userRepository.save(ximena)
            val norma: User = User(
                email = "norma@email.com",
                name = "Norma",
                password = "aw2s0meR!",
                active = true,
                userRole = mutableListOf(UserRole.USER,UserRole.ADMIN))
            userRepository.save(norma)
        }
}
