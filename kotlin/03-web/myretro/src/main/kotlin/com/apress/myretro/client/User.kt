package com.apress.myretro.client

data class User(val email: String? = null,
    val name: String? = null,
    val gravatarUrl: String? = null,
    val userRole: List<UserRole>? = null,
    val active: Boolean = false
)
