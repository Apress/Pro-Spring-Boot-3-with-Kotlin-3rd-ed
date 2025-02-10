package com.apress.users.config

import com.apress.myretro.annotations.EnableMyRetroAudit
import com.apress.users.model.User
import com.apress.users.model.UserRole
import com.apress.users.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.List

@EnableMyRetroAudit
@Configuration
@EnableConfigurationProperties(
    UserProperties::class
)
class UserConfiguration {
    @Bean
    fun init(userService: UserService): CommandLineRunner {
        return CommandLineRunner { _: Array<String> ->
            userService.saveUpdateUser(
                User(
                    "ximena@email.com",
                    "Ximena",
                    "https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar",
                    "aw2s0meR!",
                    listOf(UserRole.USER),
                    true
                )
            )
            userService.saveUpdateUser(
                User(
                    "norma@email.com",
                    "Norma",
                    "https://www.gravatar.com/avatar/f07f7e553264c9710105edebe6c465e7?d=wavatar",
                    "aw2s0meR!",
                    listOf(UserRole.USER, UserRole.ADMIN),
                    false
                )
            )
        }
    }
}
