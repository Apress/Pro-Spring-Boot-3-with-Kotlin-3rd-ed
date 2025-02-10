package com.apress.users.events

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime


data class UserRemovedEvent(
    var email: String? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    var removed: LocalDateTime? = null,
    var action:String? = "REMOVED"

)
