package com.apress.users.model

import jakarta.validation.constraints.NotBlank
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table(name = "PEOPLE")
data class User(
    @Id
    var id: UUID? = null,
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
