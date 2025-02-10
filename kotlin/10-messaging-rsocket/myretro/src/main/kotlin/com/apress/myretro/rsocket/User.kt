package com.apress.myretro.rsocket

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
    var id: UUID? = null,
    var email: String? = null,
    var name: String? = null,
    var gravatarUrl: String? = null,
    var userRole: MutableCollection<UserRole> = mutableListOf(),
    var active:Boolean = false
)
