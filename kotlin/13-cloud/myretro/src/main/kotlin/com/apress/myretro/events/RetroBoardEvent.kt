package com.apress.myretro.events

import java.time.LocalDateTime
import java.util.*

open class RetroBoardEvent @JvmOverloads constructor(
    open var retroBoardId: UUID? = null,
    open var action: RetroBoardEventAction? = null,
    open var happenAt: LocalDateTime? = null
)
