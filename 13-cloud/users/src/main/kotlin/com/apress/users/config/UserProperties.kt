package com.apress.users.config

import org.springframework.boot.context.properties.ConfigurationProperties

//@RefreshScope
@ConfigurationProperties(prefix = "user")
open class UserProperties {
    open var reportFormat: String? = null
    open var emailSubject: String? = null
    open var emailFrom: String? = null
    open var emailTemplate: String? = null
}
