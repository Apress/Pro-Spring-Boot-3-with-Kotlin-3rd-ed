package com.apress.users

import org.springframework.stereotype.Component
import java.util.*

@Component
class UserRepository : Repository<User, String> {
    private val users: MutableMap<String, User> = mutableMapOf(
        "ximena@email.com" to
            User(email = "ximena@email.com",
                name = "Ximena",
                gravatarUrl = "https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar",
                password = "aw2s0meR!",
                userRole = listOf(UserRole.USER),
                active = true
            ),
        "norma@email.com" to
            User(name = "Norma",
                email = "norma@email.com",
                gravatarUrl = "https://www.gravatar.com/avatar/f07f7e553264c9710105edebe6c465e7?d=wavatar",
                password = "aw2s0meR!",
                userRole = listOf(UserRole.USER,UserRole.ADMIN),
                active = true
            )
    )

    override fun save(user: User): User {
        user.gravatarUrl = user.gravatarUrl?: "https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar"
        //if (user.userRole == null) user.userRole = emptyList()
        users[user.email!!] = user
        return user
    }

    override fun findById(id: String): User? = users[id]

    override fun findAll(): Iterable<User> = users.values

    override fun deleteById(id: String) {
        users.remove(id)
    }
}
