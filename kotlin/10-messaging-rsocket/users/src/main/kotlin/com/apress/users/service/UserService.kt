package com.apress.users.service

import com.apress.users.model.User
import com.apress.users.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    val allUsers: Flux<User>
        get() = userRepository.findAll()

    fun findUserByEmail(email: String): Mono<User> =
        userRepository.findByEmail(email)

    fun saveUpdateUser(user: User): Mono<User> =
        userRepository.save(user)

    fun removeUserByEmail(email: String) =
        userRepository.deleteByEmail(email)
}
