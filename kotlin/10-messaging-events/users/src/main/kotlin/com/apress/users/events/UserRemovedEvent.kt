package com.apress.users.events

import java.time.LocalDateTime

data class UserRemovedEvent(
    var email: String? = null,
    var removed: LocalDateTime? = null
)
