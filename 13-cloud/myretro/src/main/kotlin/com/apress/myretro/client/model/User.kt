package com.apress.myretro.client.model

@JvmRecord
data class User(
    val email: String,
    val name: String,
    val gravatarUrl: String,
    val password: String,
    val userRole: Collection<UserRole>,
    val active: Boolean
)
