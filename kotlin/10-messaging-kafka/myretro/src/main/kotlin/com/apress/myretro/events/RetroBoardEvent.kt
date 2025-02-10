package com.apress.myretro.events

import java.time.LocalDateTime
import java.util.*

data class RetroBoardEvent(
    var retroBoardId: UUID? = null,
    var action: RetroBoardEventAction? = null,
    var happenAt: LocalDateTime? = null
)
