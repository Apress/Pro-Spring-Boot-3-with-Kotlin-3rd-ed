package com.apress.myretro.persistence

import com.apress.myretro.board.Card
import com.apress.myretro.board.RetroBoard
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono
import java.util.*

interface RetroBoardRepository : ReactiveMongoRepository<RetroBoard, UUID> {
    @Query("{'id': ?0}")
    override fun findById(id: UUID): Mono<RetroBoard>

    @Query("{}, { cards: { \$elemMatch: { _id: ?0 } } }")
    fun findRetroBoardByIdAndCardId(cardId: UUID): Mono<RetroBoard>
}
