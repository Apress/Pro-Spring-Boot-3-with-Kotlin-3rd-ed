package com.apress.users.config

import org.springframework.boot.context.properties.ConfigurationProperties

//@RefreshScope
@ConfigurationProperties(prefix = "user")
data class UserProperties(
    var reportFormat: String? = null,
    var emailSubject: String? = null,
    var emailFrom: String? = null,
    var emailTemplate: String? = null
)
