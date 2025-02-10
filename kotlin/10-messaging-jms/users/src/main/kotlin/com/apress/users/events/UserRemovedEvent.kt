package com.apress.users.events

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.time.LocalDateTime

data class UserRemovedEvent(
    var email: String? = null,

    @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @get:JsonSerialize(using = LocalDateTimeSerializer::class)
    var removed: LocalDateTime? = null
)
