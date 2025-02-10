package com.apress.users.security

import com.apress.users.exception.UserNotFoundException
import com.apress.users.model.User
import com.apress.users.model.UserRole
import com.apress.users.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.function.Supplier

class UserSecurityDetailsService(var userRepository: UserRepository) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findById(username)
            .orElseThrow{ UserNotFoundException() }
        val passwordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        return org.springframework.security.core.userdetails.User
            .withUsername(username)
            .roles(
                *user.userRole!!.map { obj: UserRole -> obj.toString() }.toTypedArray()
            )
            .password(passwordEncoder.encode(user.password))
            .accountExpired(!user.active)
            .build()
    }
}
