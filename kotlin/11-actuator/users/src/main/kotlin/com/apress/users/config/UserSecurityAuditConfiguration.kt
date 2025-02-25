package com.apress.users.config

import org.springframework.boot.actuate.audit.AuditEventRepository
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository
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
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher
import org.springframework.web.servlet.handler.HandlerMappingIntrospector


@Configuration
class UserSecurityAuditConfiguration {
    @Bean
    fun auditEventRepository(): AuditEventRepository {
        return InMemoryAuditEventRepository()
    }

    // Enable this if you want the Home page to be publicly accessible
    /*
    @Bean
    fun webSecurityCustomizer(introspector: HandlerMappingIntrospector?): WebSecurityCustomizer? {
        val mvcMatcherBuilder = MvcRequestMatcher.Builder(introspector)
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(
                mvcMatcherBuilder.pattern("/webjars/ **"),
                mvcMatcherBuilder.pattern("/index.html"),
                mvcMatcherBuilder.pattern("/")
            )
        }
    }
     */

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity, introspector: HandlerMappingIntrospector?): SecurityFilterChain {
        val mvcMatcherBuilder = MvcRequestMatcher.Builder(introspector)
        http
            .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
            .authorizeHttpRequests{ auth ->
                auth
                    .requestMatchers(mvcMatcherBuilder.pattern("/actuator/**"))
                    .hasRole("ACTUATOR") //.requestMatchers(mvcMatcherBuilder.pattern("/management/**")).hasRole("ACTUATOR")
                    .anyRequest().authenticated()
            }
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    fun userDetailsManager(passwordEncoder: PasswordEncoder): UserDetailsManager {
        val admin = User
            .builder()
            .username("admin")
            .password(passwordEncoder.encode("admin"))
            .roles("ADMIN", "USER", "ACTUATOR")
            .build()
        val manager = User
            .builder()
            .username("manager")
            .password(passwordEncoder.encode("manager"))
            .roles("ADMIN", "USER")
            .build()
        val user = User
            .builder()
            .username("user")
            .password(passwordEncoder.encode("user"))
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(manager, user, admin)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
