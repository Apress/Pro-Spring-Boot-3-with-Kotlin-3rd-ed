package com.apress.myretro.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
class RetroBoardSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        corsConfigurationSource: CorsConfigurationSource?
    ): SecurityWebFilterChain {
        http
            .csrf { csrf: ServerHttpSecurity.CsrfSpec -> csrf.disable() }
            .cors { cors: ServerHttpSecurity.CorsSpec -> cors.configurationSource(corsConfigurationSource) }
            .authorizeExchange { auth: ServerHttpSecurity.AuthorizeExchangeSpec ->
                auth
                    .pathMatchers(HttpMethod.POST, "/retros/**").hasAuthority("SCOPE_retros:write")
                    .pathMatchers(HttpMethod.DELETE, "/retros/**").hasAuthority("SCOPE_retros:write")
                    .pathMatchers("/retros/**").hasAnyAuthority("SCOPE_retros:read", "SCOPE_retros:write")
                    .pathMatchers("/", "/webjars/**").permitAll()
            }
            .oauth2Login(Customizer.withDefaults())
            .oauth2ResourceServer { config: ServerHttpSecurity.OAuth2ResourceServerSpec ->
                config.jwt(
                    Customizer.withDefaults()
                )
            }
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedMethods = mutableListOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
            allowedHeaders = mutableListOf(
                "x-ijt",
                "Set-Cookie",
                "Cookie",
                "Content-Type",
                "X-MYRETRO",
                "Allow",
                "Authorization",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials",
                "Access-Control-Allow-Headers",
                "Access-Control-Allow-Methods",
                "Access-Control-Expose-Headers",
                "Access-Control-Max-Age",
                "Access-Control-Request-Headers",
                "Access-Control-Request-Method",
                "Origin",
                "X-Requested-With",
                "Accept",
                "Accept-Encoding",
                "Accept-Language",
                "Host",
                "Referer",
                "Connection",
                "User-Agent"
            )
            exposedHeaders = mutableListOf(
                "x-ijt",
                "Set-Cookie",
                "Cookie",
                "Content-Type",
                "X-MYRETRO",
                "Allow",
                "Authorization",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials",
                "Access-Control-Allow-Headers",
                "Access-Control-Allow-Methods",
                "Access-Control-Expose-Headers",
                "Access-Control-Max-Age",
                "Access-Control-Request-Headers",
                "Access-Control-Request-Method",
                "Origin",
                "X-Requested-With",
                "Accept",
                "Accept-Encoding",
                "Accept-Language",
                "Host",
                "Referer",
                "Connection",
                "User-Agent"
            )
            allowedOriginPatterns = mutableListOf("http://localhost:*")
            allowCredentials = true
        }
        return UrlBasedCorsConfigurationSource().apply { registerCorsConfiguration("/**", configuration) }
    }
}
