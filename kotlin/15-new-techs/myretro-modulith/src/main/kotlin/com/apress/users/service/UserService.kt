package com.apress.users.service

import com.apress.users.UserEvent
import com.apress.users.actuator.LogEventEndpoint
import com.apress.users.model.User
import com.apress.users.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var publisher: ApplicationEventPublisher
    @Autowired
    private lateinit var logEventsEndpoint: LogEventEndpoint
    @Autowired
    private lateinit var events: ApplicationEventPublisher

    val allUsers: Iterable<User>
        get() = userRepository.findAll()

    fun findUserByEmail(email: String): Optional<User> {
        return userRepository.findById(email)
    }

    @Transactional
    fun saveUpdateUser(user: User): User {
        val userResult: User = userRepository.save<User>(user)

        // Only when the user is saved we do publish the event
        //events.publishEvent(user);


        //Fix - create a UserEvent class at the same level as user package.
        events.publishEvent(UserEvent(user.email, "save"))
        return userResult
    }

    fun removeUserByEmail(email: String) {
        userRepository.deleteById(email)
    }
}
