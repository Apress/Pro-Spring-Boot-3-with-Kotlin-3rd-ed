package com.apress.users.service

import com.apress.users.actuator.LogEventEndpoint
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
    @Autowired
    private lateinit var logEventsEndpoint: LogEventEndpoint

    val allUsers: Iterable<User>
        get() = userRepository.findAll()

    fun findUserByEmail(email: String): Optional<User> {
        return userRepository.findById(email)
    }

    fun saveUpdateUser(user: User): User {
        val userResult: User = userRepository.save<User>(user)
        if (logEventsEndpoint.isEnable) publisher.publishEvent(
            UserActivatedEvent(
                userResult.email,
                userResult.active
            )
        )
        return userResult
    }

    fun removeUserByEmail(email: String) {
        userRepository.deleteById(email)
        if (logEventsEndpoint.isEnable) publisher.publishEvent(UserRemovedEvent(email, LocalDateTime.now()))
    }
}
