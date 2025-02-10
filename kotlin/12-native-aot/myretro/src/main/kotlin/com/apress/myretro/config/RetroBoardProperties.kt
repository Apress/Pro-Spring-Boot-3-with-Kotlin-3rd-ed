package com.apress.myretro.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty

@ConfigurationProperties(prefix = "myretro")
class RetroBoardProperties {
    @NestedConfigurationProperty
    var usersService: UsersService? = null
}
