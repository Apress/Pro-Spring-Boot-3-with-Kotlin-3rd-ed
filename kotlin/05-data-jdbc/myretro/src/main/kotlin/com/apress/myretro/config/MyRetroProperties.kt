package com.apress.myretro.config

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "service")
data class MyRetroProperties(
    var users: UsersConfiguration? = null
)
