package com.apress.myretro.board

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.*

data class RetroBoard(
    var id: UUID? = null,
    @get:NotNull
    @get:NotBlank(message = "A name must be provided")
    val name: String? = null,
    var cards: List<Card> = mutableListOf()
)
