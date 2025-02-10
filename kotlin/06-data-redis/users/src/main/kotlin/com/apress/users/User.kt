package com.apress.users

import jakarta.validation.constraints.NotBlank
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("USERS")
data class User(
    @Id
    @get:NotBlank(message = "Email can not be empty")
    var email:  String? = null,

    @get:NotBlank(message = "Name can not be empty")
    var name:  String? = null,

    var gravatarUrl: String? = null,

    @get:NotBlank(message = "Password can not be empty")
    var password:  String? = null,

    var userRole: Collection<UserRole>? = null,
    var active:Boolean = false
)
