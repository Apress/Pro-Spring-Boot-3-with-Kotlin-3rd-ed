package com.apress.users.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank

@Entity(name = "PEOPLE")
data class User(
    @Id
    @get:NotBlank(message = "Email can not be empty")
    var email:  String? = null,

    @get:NotBlank(message = "Name can not be empty")
    var name: String? = null,

    val gravatarUrl: String? = null,

    @get:NotBlank(message = "Password can not be empty")
    var password: String? = null,

    var userRole: MutableCollection<UserRole> = mutableListOf(),
    var active:Boolean = false
)
