package com.apress.myretro.client

@JvmRecord
data class User(
    val email: String,
    val name: String,
    val password: String,
    val gravatarUrl: String,
    val userRole: List<UserRole>,
    val active: Boolean
)
