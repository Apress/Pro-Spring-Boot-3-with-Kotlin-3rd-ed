package com.apress.users.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
class UserSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { c: CsrfConfigurer<HttpSecurity?> -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) }
            .authorizeHttpRequests{ auth ->
                    auth.requestMatchers(
                            AntPathRequestMatcher("/index.html"),
                            AntPathRequestMatcher("/webjars/**"),
                            AntPathRequestMatcher("/error")
                        ).permitAll()
                        .anyRequest().authenticated()
                }
            .logout { l: LogoutConfigurer<HttpSecurity?> -> l.logoutSuccessUrl("/index.html").permitAll() }
            .oauth2Login(Customizer.withDefaults())
        return http.build()
    }
}
