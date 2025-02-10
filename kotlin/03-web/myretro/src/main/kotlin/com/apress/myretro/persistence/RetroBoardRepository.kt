package com.apress.myretro.persistence

import com.apress.myretro.board.Card
import com.apress.myretro.board.CardType
import com.apress.myretro.board.RetroBoard
import org.springframework.stereotype.Component
import java.util.*

@Component
class RetroBoardRepository : Repository<RetroBoard, UUID> {
    private val retroBoardMap = mutableMapOf(
        UUID.fromString("9DC9B71B-A07E-418B-B972-40225449AFF2") to
        RetroBoard(id=UUID.fromString("9DC9B71B-A07E-418B-B972-40225449AFF2"),
            name = "Spring Boot 3.0 Meeting",
            cards = listOf(
                Card(
                    id = UUID.fromString("BB2A80A5-A0F5-4180-A6DC-80C84BC014C9"),
                    comment = "Happy to meet the team",
                    cardType = CardType.HAPPY),
                Card(
                    id = UUID.fromString("011EF086-7645-4534-9512-B9BC4CCFB688"),
                    comment = "New projects",
                    cardType = CardType.HAPPY),
                Card(
                    id = UUID.fromString("775A3905-D6BE-49AB-A3C4-EBE287B51539"),
                    comment = "When to meet again??",
                    cardType = CardType.MEH),
                Card(
                    id = UUID.fromString("896C093D-1C50-49A3-A58A-6F1008789632"),
                    comment = "We need more time to finish",
                    cardType = CardType.SAD)
            )
        )
    )

    override fun save(domain: RetroBoard): RetroBoard {
        domain.id = domain.id ?: UUID.randomUUID()
        retroBoardMap[domain.id] = domain
        return domain
    }

    override fun findById(uuid: UUID): RetroBoard = retroBoardMap[uuid]!!

    override fun findAll(): Iterable<RetroBoard> = retroBoardMap.values

    override fun delete(uuid: UUID) {
        retroBoardMap.remove(uuid)
    }
}
