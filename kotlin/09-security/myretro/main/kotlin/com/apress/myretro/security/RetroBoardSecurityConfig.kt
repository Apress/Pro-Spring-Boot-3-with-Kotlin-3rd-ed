package com.apress.myretro.security

import com.apress.myretro.client.User
import com.apress.myretro.client.UserClient
import com.apress.myretro.client.UserRole
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatusCode
import org.springframework.http.ReactiveHttpOutputMessage
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.Customizer
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.server.WebSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.stream.Collectors

@Configuration
class RetroBoardSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        reactiveAuthenticationManager: ReactiveAuthenticationManager?,
        corsConfigurationSource: CorsConfigurationSource?
    ): SecurityWebFilterChain {
        http
            .csrf{ csrf: ServerHttpSecurity.CsrfSpec -> csrf.disable() }
            .cors{ cors: ServerHttpSecurity.CorsSpec -> cors.configurationSource(corsConfigurationSource) }
            .authorizeExchange { auth: ServerHttpSecurity.AuthorizeExchangeSpec ->
                auth
                    .pathMatchers(HttpMethod.POST, "/retros/**").hasRole("ADMIN")
                    .pathMatchers(HttpMethod.DELETE, "/retros/**").hasRole("ADMIN")
                    .pathMatchers("/retros/**").hasAnyRole("USER", "ADMIN")
                    .pathMatchers("/", "/webjars/**").permitAll()
            }
            .authenticationManager(reactiveAuthenticationManager)
            .formLogin{ formLoginSpec: ServerHttpSecurity.FormLoginSpec ->
                formLoginSpec.authenticationSuccessHandler(
                    serverAuthenticationSuccessHandler()
                )
            }
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    fun serverAuthenticationSuccessHandler(): ServerAuthenticationSuccessHandler {
        return ServerAuthenticationSuccessHandler { webFilterExchange: WebFilterExchange, authentication: Authentication ->
            webFilterExchange.exchange.session
                .flatMap{ session: WebSession ->
                    val (email, name, password, gravatarUrl) = authentication.details as User
                    val body = """
                {
                    "email": "%s",
                    "name": "%s",
                    "password": "%s",
                    "userRole": "%s",
                    "gravatarUrl": "%s",
                    "active": %s
                }
                
                """.trimIndent().format(
                        email,
                        name,
                        password,
                        authentication.authorities.stream()
                            .map { obj: GrantedAuthority -> obj.authority }
                            .map { role: String -> role.replace("ROLE_", "") }
                            .collect(Collectors.joining(",")),
                        gravatarUrl,
                        true)
                    webFilterExchange.exchange.response.setStatusCode(HttpStatusCode.valueOf(200))
                    val response: ReactiveHttpOutputMessage = webFilterExchange.exchange.response
                    response.headers.add("Content-Type", "application/json")
                    response.headers.add("X-MYRETRO", "SESSION=" + session.id + "; Path=/; HttpOnly; SameSite=Lax")
                    val dataBufferPublisher: DataBuffer = response.bufferFactory().wrap(body.toByteArray())
                    response.writeAndFlushWith(Flux.just<DataBuffer>(dataBufferPublisher).windowUntilChanged())
                }
        }
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
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun reactiveAuthenticationManager(userClient: UserClient): ReactiveAuthenticationManager {
        return ReactiveAuthenticationManager { authentication: Authentication ->
            val username = authentication.name
            val password = authentication.credentials.toString()
            val userResult: Mono<User> = userClient.getUserInfo(username)
            userResult.flatMap{ user: User ->
                if (user.password == password) {
                    val grantedAuthorities: List<GrantedAuthority> = user.userRole.stream()
                        .map { obj: UserRole -> obj.name }
                        .map { str: String -> "ROLE_$str" }
                        .map { role: String? ->
                            SimpleGrantedAuthority(
                                role
                            )
                        }
                        .collect(Collectors.toList<GrantedAuthority>())
                    val authenticationToken =
                        UsernamePasswordAuthenticationToken(username, password, grantedAuthorities)
                    authenticationToken.details = user
                    return@flatMap Mono.just<UsernamePasswordAuthenticationToken>(authenticationToken)
                } else {
                    return@flatMap Mono.error<Authentication>(BadCredentialsException("Invalid username or password"))
                }
            }
        }
    }
}
