package com.apress.security.oauth2

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
class Oauth2Config {
    @Bean
    fun inMemoryUserDetailsManager(passwordEncoder: PasswordEncoder): InMemoryUserDetailsManager {
        val admin = User
            .builder()
            .username("admin")
            .password(passwordEncoder.encode("admin"))
            .authorities("users.read", "users.write")
            .build()
        val manager = User
            .builder()
            .username("manager@email.com")
            .password(passwordEncoder.encode("aw2s0meR!"))
            .authorities("users.read", "users.write")
            .build()
        val user = User
            .builder()
            .username("user@email.com")
            .password(passwordEncoder.encode("aw2s0meR!"))
            .authorities("users.read")
            .build()
        return InMemoryUserDetailsManager(manager, user, admin)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
