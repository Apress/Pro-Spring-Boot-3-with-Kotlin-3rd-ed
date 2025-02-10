package com.apress.myretro.amqp

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserEvent(
    var action: String? = null,
    var email: String? = null,
    var active:Boolean = false,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    var removed: LocalDateTime? = null
)
