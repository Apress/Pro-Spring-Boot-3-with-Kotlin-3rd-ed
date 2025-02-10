package com.apress.users.config

import com.apress.users.model.User
import com.apress.users.model.UserRole
import com.apress.users.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class UserConfig {
    @Bean
    fun init(userRepository: UserRepository): CommandLineRunner {
        return CommandLineRunner { _: Array<String> ->
            userRepository.save(
                User(
                    "ximena@email.com",
                    "Ximena",
                    "https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar",
                    "aw2s0meR!",
                    mutableListOf(UserRole.USER),
                    true
                )
            )
            userRepository.save(
                User(
                    "norma@email.com",
                    "Norma",
                    "https://www.gravatar.com/avatar/f07f7e553264c9710105edebe6c465e7?d=wavatar",
                    "aw2s0meR!",
                    mutableListOf(UserRole.ADMIN),
                    true
                )
            )
            userRepository.save(
                User(
                    "manager@email.com",
                    "Manager",
                    "https://www.gravatar.com/avatar/f07f7e553264c9710102edebe6c465e7?d=wavatar",
                    "aw2s0meR!",
                    mutableListOf(UserRole.ADMIN),
                    true
                )
            )
        }
    }
}
