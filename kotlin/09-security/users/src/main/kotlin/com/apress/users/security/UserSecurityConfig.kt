package com.apress.users.security


// Version 1 - In-Memory Security
/*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
class UserSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
            .authorizeHttpRequests(Customizer { auth ->
                auth.anyRequest().authenticated()
            })
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    fun userDetailsManager(passwordEncoder: PasswordEncoder): UserDetailsManager {
        val admin = User
            .builder()
            .username("admin")
            .password(passwordEncoder.encode("admin"))
            .roles("ADMIN", "USER")
            .build()
        val manager = User
            .builder()
            .username("manager@email.com")
            .password(passwordEncoder.encode("aw2s0meR!"))
            .roles("ADMIN", "USER")
            .build()
        val user = User
            .builder()
            .username("user@email.com")
            .password(passwordEncoder.encode("aw2s0meR!"))
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(manager, user, admin)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
*/

// Version 2 - Default Security Schema
/*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import javax.sql.DataSource


@Configuration
class UserSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
            .authorizeHttpRequests(Customizer { auth ->
                auth.anyRequest().authenticated()
            })
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    fun dataSource(): DataSource {
        return EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
            .build()
    }

    @Bean
    fun userDetailsManager(passwordEncoder: PasswordEncoder, dataSource: DataSource?): UserDetailsManager {
        val admin = User
            .builder()
            .username("admin")
            .password(passwordEncoder.encode("admin"))
            .roles("ADMIN", "USER")
            .build()
        val manager = User
            .builder()
            .username("manager@email.com")
            .password(passwordEncoder.encode("aw2s0meR!"))
            .roles("ADMIN", "USER")
            .build()
        val user = User
            .builder()
            .username("user@email.com")
            .password(passwordEncoder.encode("aw2s0meR!"))
            .roles("USER")
            .build()
        val users = JdbcUserDetailsManager(dataSource)
        users.createUser(admin)
        users.createUser(manager)
        users.createUser(user)
        return users
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
*/

// Version 3 - Custom Security

import com.apress.users.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.web.SecurityFilterChain


@Configuration
class UserSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity, authenticationProvider: AuthenticationProvider?): SecurityFilterChain {
        http
            .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
            .authorizeHttpRequests(Customizer { auth ->
                auth.anyRequest().authenticated()
            })
            .authenticationProvider(authenticationProvider)
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    fun authenticationProvider(userRepository: UserRepository?): AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(UserSecurityDetailsService(userRepository!!))
        return provider
    }
}

// Version 4 - With UI connectivity
/*
import com.apress.users.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class UserSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun filterChain(
        http: HttpSecurity, authenticationProvider: AuthenticationProvider?,
        corsConfigurationSource: CorsConfigurationSource?
    ): SecurityFilterChain {
        http
            .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
            .cors { cors: CorsConfigurer<HttpSecurity?> -> cors.configurationSource(corsConfigurationSource) }
            .authorizeHttpRequests{ auth ->
                auth.anyRequest().authenticated()
            }
            .authenticationProvider(authenticationProvider)
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = mutableListOf("*")
            allowedMethods = mutableListOf("*")
            allowedHeaders = mutableListOf("*")
        }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration(" / * *", configuration)
        }
    }

    @Bean
    fun authenticationProvider(userRepository: UserRepository): AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(UserSecurityDetailsService(userRepository))
        return provider
    }
}
*/
