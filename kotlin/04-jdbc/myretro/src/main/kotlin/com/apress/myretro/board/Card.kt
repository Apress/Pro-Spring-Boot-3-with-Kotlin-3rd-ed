package com.apress.myretro.board

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.*

data class Card(
    var id: UUID? = null,

    @get:NotBlank(message = "A comment must be provided always")
    @get:NotNull
    var comment:  String? = null,

    @get:NotNull(message = "A CarType HAPPY|MEH|SAD must be provided")
    var cardType:  CardType? = null,
    var retroBoardId: UUID? = null
)
