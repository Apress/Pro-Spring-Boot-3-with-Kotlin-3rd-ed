package com.apress.myretro.board

import java.util.*

class RetroBoard(var uuid: UUID? = null, val name: String? = null, val cards: MutableList<Card> = mutableListOf()){
}
