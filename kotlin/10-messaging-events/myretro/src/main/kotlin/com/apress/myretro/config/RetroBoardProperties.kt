package com.apress.myretro.config

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "myretro")
data class RetroBoardProperties(
    var usersService: UsersService? = null
)

data class UsersService(
    var hostname: String? = null,
    var basePath: String? = null
)
