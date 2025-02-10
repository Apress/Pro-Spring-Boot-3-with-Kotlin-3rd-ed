package com.apress.myretro.board

import java.util.*

data class Card(
    var id: UUID? = null,
    var comment: String? = null,
    var cardType: CardType? = null
)
