package com.apress.users.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank


@Entity(name = "PEOPLE")
open class User(
    @Id
    @get:NotBlank(message = "Email can not be empty")
    open var email: String? = null,

    @get:NotBlank(message = "Name can not be empty")
    open var name:  String? = null,

    open var gravatarUrl: String? = null,

    @get:NotBlank(message = "Password can not be empty")
    open var password: String? = null,

    open var userRole: Collection<UserRole> = mutableListOf(),

    open var active:Boolean = false
)
