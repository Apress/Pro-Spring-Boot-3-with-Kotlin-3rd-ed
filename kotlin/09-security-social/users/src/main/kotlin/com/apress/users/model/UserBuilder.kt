package com.apress.users.model

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator

class UserBuilder private constructor(private var user: User, private var validator: Validator? = null) {
    fun withEmail(email: String?) = this.apply { user.email = email }
    fun withPassword(password: String?) = this.apply { user.password = password }
    fun withName(name: String?) = this.apply { user.name = name }
    fun withRoles(vararg userRoles: UserRole) = this.apply { user.userRole = userRoles.toMutableList() }
    fun active() = this.apply { user.active = true }
    fun inactive() = this.apply { user.active = false }

    fun build(): User {
        if (validator != null) {
            val constraintViolation = validator!!.validate(user)
            if (constraintViolation.isNotEmpty()) {
                throw ConstraintViolationException(constraintViolation)
            }
        }
        user.gravatarUrl = user.gravatarUrl ?: UserGravatar.getGravatarUrlFromEmail(user.email!!)
        return user
    }

    companion object {
        fun createUser(v: Validator? = null): UserBuilder = UserBuilder(
            User(
                "dummy@email.com",
                "Dummy",
                UserGravatar.getGravatarUrlFromEmail("dummy@email.com"),
                "aw2s0me",
                userRole = mutableListOf(UserRole.USER),
                true),
            validator = v
        )
    }
}
