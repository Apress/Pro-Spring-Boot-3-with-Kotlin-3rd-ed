package com.apress.users.service

import com.apress.myretro.annotations.MyRetroAudit
import com.apress.myretro.annotations.MyRetroAuditOutputFormat
import com.apress.users.actuator.LogEventEndpoint
import com.apress.users.model.User
import com.apress.users.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
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

    fun findUserByEmail(email: String): Optional<User> =
        userRepository.findById(email)

    @MyRetroAudit(
        showArgs = true,
        message = "Saving or updating user",
        format = MyRetroAuditOutputFormat.JSON,
        prettyPrint = true
    )
    fun saveUpdateUser(user: User): User =
        userRepository.save<User>(user)

    fun removeUserByEmail(email: String) =
        userRepository.deleteById(email)
}
