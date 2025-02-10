package com.apress.users

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.util.*

@Configuration
class UserConfiguration {
    @Bean
    @Profile("default")
    fun init(userRepository: UserRepository): CommandLineRunner {
        return CommandLineRunner { _: Array<String> ->
            userRepository.save(
                User(
                    "ximena@email.com",
                    "Ximena",
                    "https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar",
                    "aw2s0me",
                    mutableListOf(UserRole.USER),
                    true
                )
            )
            userRepository.save(
                User(
                    "norma@email.com",
                    "Norma",
                    "https://www.gravatar.com/avatar/f07f7e553264c9710105edebe6c465e7?d=wavatar",
                    "aw2s0me",
                    mutableListOf(UserRole.USER, UserRole.ADMIN),
                    true
                )
            )
        }
    }
}
