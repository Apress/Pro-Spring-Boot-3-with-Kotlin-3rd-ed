package com.apress.users.stream

import com.apress.users.config.UserProperties
import com.apress.users.model.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Supplier

@Configuration
class UserSource(private val userProperties: UserProperties) {
    @Bean
    fun processor(): Supplier<User> {
        return Supplier { User() }
    }
}
