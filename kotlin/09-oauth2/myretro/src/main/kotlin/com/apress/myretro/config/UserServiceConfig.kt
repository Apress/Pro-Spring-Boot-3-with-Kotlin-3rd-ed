package com.apress.myretro.config

data class UserServiceConfig(
    var server: String? = null,
    var port: Int? = null,
    var username: String? = null,
    var password: String? = null
)
