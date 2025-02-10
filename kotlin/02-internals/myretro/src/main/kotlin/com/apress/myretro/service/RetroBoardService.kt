package com.apress.myretro.service

import com.apress.myretro.board.Card
import com.apress.myretro.board.CardType
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.persistence.Repository
import java.util.*

class RetroBoardService(private val repository: Repository<RetroBoard, UUID>) {
    fun findAllHappyCardsFromRetroBoardId(uuid: UUID): Iterable<Card> {
        val retroBoard = repository.findById(uuid)!!
        return retroBoard.cards.stream().filter { card -> card.cardType == CardType.HAPPY }.toList()
    }
}
