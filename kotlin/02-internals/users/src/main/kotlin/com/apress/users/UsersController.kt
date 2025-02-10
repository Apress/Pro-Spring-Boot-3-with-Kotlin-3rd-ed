package com.apress.users

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController {
    private val users: MutableMap<String?, User> = mutableMapOf(
        "ximena@email.com" to User("ximena@email.com", "Ximena"),
        "norma@email.com" to User("norma@email.com", "Norma")
    )

    @get:GetMapping
    val all: Collection<User>
        get() = users.values

    @GetMapping("/{email}")
    fun findUserByEmail(@PathVariable email: String): User? {
        return users[email]
    }

    @PostMapping
    fun save(@RequestBody user: User): User {
        users[user.email] = user
        return user
    }

    @DeleteMapping("/{email}")
    fun save(@PathVariable email: String) {
        users.remove(email)
    }
}
