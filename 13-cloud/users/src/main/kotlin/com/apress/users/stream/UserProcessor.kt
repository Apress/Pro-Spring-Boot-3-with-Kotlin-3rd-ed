package com.apress.users.stream

import com.apress.users.model.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Function

@Configuration
class UserProcessor {
    @Bean
    fun process(): Function<String, User> {
        return Function { _: String -> User() }
    }
}
