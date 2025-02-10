package com.apress.myretro.web.socket

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class Event(
    var version: String? = null,

    @get:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    var time: LocalDateTime? = null,

    var event: UserEvent? = null
)
