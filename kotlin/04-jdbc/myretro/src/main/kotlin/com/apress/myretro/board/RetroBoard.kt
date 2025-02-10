package com.apress.myretro.board

import jakarta.validation.constraints.NotBlank
import java.util.*

data class RetroBoard(
    var id: UUID? = null,

    @get:NotBlank(message = "A name must be provided")
    var name:  String? = null,

    var cards: MutableMap<UUID, Card> = mutableMapOf()
)
