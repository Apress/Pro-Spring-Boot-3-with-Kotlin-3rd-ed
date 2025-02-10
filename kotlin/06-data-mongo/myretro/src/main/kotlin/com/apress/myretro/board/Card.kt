package com.apress.myretro.board

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class Card(
    @get:NotNull
    var id: java.util.UUID? = null,

    @get:NotBlank
    var comment: String? = null,

    @get:NotNull
    var cardType: CardType? = null
)
