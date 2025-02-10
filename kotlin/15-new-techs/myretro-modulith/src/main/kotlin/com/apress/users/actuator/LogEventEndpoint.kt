package com.apress.users.actuator

import org.springframework.boot.actuate.endpoint.annotation.Endpoint
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component

@Component
@Endpoint(id = "event-config")
class LogEventEndpoint {
    private val config = LogEventConfig()

    @ReadOperation
    fun config(): LogEventConfig = config

    @WriteOperation
    fun eventConfig(@Nullable enabled: Boolean?, @Nullable prefix: String?, @Nullable postfix: String?) {
        if (enabled != null) config.enabled = enabled
        if (prefix != null) config.prefix = prefix
        if (postfix != null) config.postfix = postfix
    }

    val isEnable: Boolean
        get() = config.enabled
}
