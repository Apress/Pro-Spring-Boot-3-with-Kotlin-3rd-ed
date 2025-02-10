package com.apress.myretro.web.socket

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserEvent(
    var email: String? = null,
    var active:Boolean = false,
    var action: String? = null,

    @get:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    var datetime: LocalDateTime? = null
)
