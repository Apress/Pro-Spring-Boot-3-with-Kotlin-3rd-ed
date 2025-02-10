package com.apress.myretro.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "service")
class MyRetroProperties {
    var users: Users? = null
}

data class Users(var server: String? = null,
    var port: Int? = null,
    var username: String? = null,
    var password: String? = null
)