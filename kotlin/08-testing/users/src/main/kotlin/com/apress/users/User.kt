package com.apress.users

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator
import jakarta.validation.constraints.NotBlank

@Entity(name = "PEOPLE")
data class User(
    @Id
    @get:NotBlank(message = "Email can not be empty")
    var email: String? = null,

    @get:NotBlank(message = "Name can not be empty")
    var name:  String? = null,

    var gravatarUrl: String? = null,

    @get:NotBlank(message = "Password can not be empty")
    var password: String? = null,

    var userRole: MutableCollection<UserRole> = mutableListOf(),
    var active:Boolean = false
){
    fun setUserRoleArr(vararg userRoles: UserRole) {
        userRole = mutableListOf(*userRoles)
    }
}

class UserBuilder private constructor(private var user: User,
                                      private var validator: Validator? = null) {
    fun withEmail(email: String?) = this.apply { user.email = email }
    fun withPassword(password: String?) = this.apply { user.password = password }
    fun withName(name: String?) = this.apply { user.name = name }
    fun withRoles(vararg userRoles: UserRole) = this.apply { user.setUserRoleArr(*userRoles) }
    fun active() = this.apply { user.active = true }
    fun inactive() = this.apply { user.active = false }

    fun build(): User {
        val valRes = validator?.validate(user)
        if(!valRes.isNullOrEmpty()){ throw ConstraintViolationException(valRes) }
        user.gravatarUrl = UserGravatar.getGravatarUrlFromEmail(user.email!!)
        return user
    }

    companion object {
        fun createUser(v: Validator? = null): UserBuilder = UserBuilder(User(),v)
    }
}
