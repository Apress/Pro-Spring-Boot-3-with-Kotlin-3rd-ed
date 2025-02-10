package com.apress.myretro.board

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.Id
import java.util.*

data class Card(
    @Id
    var id: UUID? = null,

    @get:NotBlank
    var comment:  String? = null,

    @get:NotNull
    var cardType:  CardType? = null
)
