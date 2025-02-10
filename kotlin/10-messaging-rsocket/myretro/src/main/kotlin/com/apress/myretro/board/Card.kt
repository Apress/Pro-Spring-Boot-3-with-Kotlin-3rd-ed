package com.apress.myretro.board

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class Card(
    @get:NotNull var cardId: java.util.UUID? = null,
    @get:NotBlank var comment: String? = null,
    @get:NotNull var cardType: CardType? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @get:NotNull var created: LocalDateTime? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @get:NotNull var modified: LocalDateTime? = null
)
