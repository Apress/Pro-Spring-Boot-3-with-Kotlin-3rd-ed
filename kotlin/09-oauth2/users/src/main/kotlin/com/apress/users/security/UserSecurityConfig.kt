package com.apress.users.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

/*

import com.apress.users.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class UserSecurityConfig {

   @Bean
   SecurityFilterChain filterChain(HttpSecurity http,AuthenticationProvider authenticationProvider) throws Exception {
       http
               .csrf(csrf -> csrf.disable())
               .authorizeHttpRequests( auth -> auth.anyRequest().authenticated())
               .authenticationProvider(authenticationProvider)
               .httpBasic(Customizer.withDefaults());

       return http.build();
   }


   @Bean
   public AuthenticationProvider authenticationProvider(UserRepository userRepository){
       DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       provider.setUserDetailsService(new UserSecurityDetailsService(userRepository));
       return provider;
   }
}
*/
/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;

@Configuration
public class UserSecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests( auth -> auth.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder){
        UserDetails norma = User
                .builder()
                .username("norma@email.com")
                .password(passwordEncoder.encode("aw2s0meR!"))
                .roles("ADMIN","USER")
                .build();

        UserDetails ximena = User
                .builder()
                .username("ximena@email.com")
                .password(passwordEncoder.encode("aw2s0meR!"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(norma,ximena);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
*/
/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class UserSecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests( auth -> auth.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/ **", configuration);
        return source;
    }

    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder){
        UserDetails admin = User
                .builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN","USER")
                .build();

        UserDetails norma = User
                .builder()
                .username("norma@email.com")
                .password(passwordEncoder.encode("aw2s0meR!"))
                .roles("ADMIN","USER")
                .build();

        UserDetails ximena = User
                .builder()
                .username("ximena@email.com")
                .password(passwordEncoder.encode("aw2s0meR!"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(norma,ximena,admin);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
*/
//
@Configuration
class UserSecurityConfig {
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity -> web.ignoring().requestMatchers("/webjars/**") }
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity, corsConfigurationSource: CorsConfigurationSource?): SecurityFilterChain {
        http
            .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
            .cors { cors: CorsConfigurer<HttpSecurity?> -> cors.configurationSource(corsConfigurationSource) }
            .authorizeHttpRequests{ auth ->
                auth
                    .requestMatchers("/users/**").hasAnyAuthority("SCOPE_users.read", "SCOPE_users.write")
                    .requestMatchers("/logout").permitAll()
            }
            .oauth2Login { login: OAuth2LoginConfigurer<HttpSecurity?> ->
                login
                    .loginPage("/oauth2/authorization/users-client-authorization-code")
                    .defaultSuccessUrl("/users")
                    .permitAll()
            }
            .oauth2ResourceServer { config: OAuth2ResourceServerConfigurer<HttpSecurity?> -> config.jwt(Customizer.withDefaults()) }
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = mutableListOf("*")
        configuration.setAllowedMethods(mutableListOf("*"))
        configuration.allowedHeaders = mutableListOf("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}