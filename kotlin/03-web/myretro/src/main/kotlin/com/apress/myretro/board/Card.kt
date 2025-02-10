package com.apress.myretro.board

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.*

data class Card(
    var id: UUID? = null,
    @get:NotBlank(message = "A comment must be provided always")
    @get:NotNull
    val comment:  String? = null,
    @get:NotNull(message = "A CarType HAPPY|MEH|SAD must be provided")
    val cardType:  CardType? = null
)
