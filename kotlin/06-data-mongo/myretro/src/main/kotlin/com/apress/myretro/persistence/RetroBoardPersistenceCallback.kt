package com.apress.myretro.persistence

import com.apress.myretro.board.RetroBoard
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback
import org.springframework.stereotype.Component
import java.util.*

@Component
class RetroBoardPersistenceCallback : BeforeConvertCallback<RetroBoard> {
    override fun onBeforeConvert(retroBoard: RetroBoard, s: String): RetroBoard =
        retroBoard.also { it.id = it.id ?: UUID.randomUUID() }
}
