package com.apress.myretro.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "myretro.audit")
data class MyRetroAuditProperties(
    /**
     * The prefix to use for all audit messages.
     */
    var prefix:String = "[AUDIT] ",

    /**
     * The file to use for audit messages.
     */
    var file:String = "myretro.events",

    /*
     * User logger instead of standard print out.
     */
    var useLogger:Boolean = false
)
