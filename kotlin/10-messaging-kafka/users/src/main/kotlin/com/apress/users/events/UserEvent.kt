package com.apress.users.events

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class UserEvent(
    var action: String? = null,
    var email: String? = null,
    var active:Boolean = false,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    var timestamp: LocalDateTime? = null
)
