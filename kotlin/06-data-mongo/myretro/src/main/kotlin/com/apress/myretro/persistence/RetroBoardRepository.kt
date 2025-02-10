package com.apress.myretro.persistence

import com.apress.myretro.board.RetroBoard
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.*

interface RetroBoardRepository : MongoRepository<RetroBoard, UUID> {
    @Query("{'id': ?0}")
    abstract override fun findById(id: UUID): Optional<RetroBoard>

    @Query("{}, { cards: { \$elemMatch: { _id: ?0 } } }")
    abstract fun findRetroBoardByCardId(cardId: UUID): Optional<RetroBoard>
}
