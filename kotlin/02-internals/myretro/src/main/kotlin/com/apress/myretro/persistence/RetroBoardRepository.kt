package com.apress.myretro.persistence

import com.apress.myretro.board.Card
import com.apress.myretro.board.CardType
import com.apress.myretro.board.RetroBoard
import java.util.*

class RetroBoardRepository : Repository<RetroBoard, UUID> {
    var boards: MutableMap<UUID, RetroBoard> = mutableMapOf(
        UUID.fromString("66D4A370-C312-4426-9C39-B411D0E43DAB")
                to RetroBoard(
                    UUID.randomUUID(), "Demo 1", Arrays.asList(
                        Card("This is awesome", CardType.HAPPY),
                        Card("I wondering when to meet again", CardType.MEH),
                        Card("Not enough timee", CardType.SAD)
                    )
                )
            )

    override fun save(domain: RetroBoard): RetroBoard? {
        val uuid = UUID.randomUUID()
        if (domain.uuid == null) domain.uuid = uuid
        return boards.put(domain.uuid!!, domain)
    }

    override fun findById(uuid: UUID): RetroBoard? {
        return boards[uuid]
    }

    override fun findAll(): MutableIterator<RetroBoard> {
        return boards.values.iterator()
    }

    override fun delete(uuid: UUID) {
        boards.remove(uuid)
    }
}
