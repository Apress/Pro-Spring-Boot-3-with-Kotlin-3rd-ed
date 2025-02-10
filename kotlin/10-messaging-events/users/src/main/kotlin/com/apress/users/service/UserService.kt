package com.apress.users.service

import com.apress.users.events.UserActivatedEvent
import com.apress.users.events.UserRemovedEvent
import com.apress.users.model.User
import com.apress.users.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var publisher: ApplicationEventPublisher

    val allUsers: Iterable<User>
        get() = userRepository.findAll()

    fun findUserByEmail(email: String): Optional<User> =
        userRepository.findById(email)

    fun saveUpdateUser(user: User): User =
        userRepository.save<User>(user).also {
            publisher.publishEvent(UserActivatedEvent(it.email, it.active))
        }

    fun removeUserByEmail(email: String) =
        userRepository.deleteById(email).also {
            publisher.publishEvent(UserRemovedEvent(email, LocalDateTime.now()))
        }
}
