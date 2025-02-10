package com.apress.myretro.jms

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserEvent(
    var email: String? = null,
    var action: String? = null,
    var active:Boolean = false,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    private val removed: LocalDateTime? = null
)
