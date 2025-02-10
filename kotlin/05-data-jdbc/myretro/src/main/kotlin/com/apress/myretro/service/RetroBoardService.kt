package com.apress.myretro.service

import com.apress.myretro.board.Card
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.persistence.RetroBoardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class RetroBoardService {
    @Autowired
    lateinit var retroBoardRepository: RetroBoardRepository

    fun save(domain: RetroBoard): RetroBoard {
        return retroBoardRepository.save<RetroBoard>(domain)
    }

    fun findById(uuid: UUID): RetroBoard {
        return retroBoardRepository.findById(uuid).get()
    }

    fun findAll(): Iterable<RetroBoard> {
        return retroBoardRepository.findAll()
    }

    fun delete(uuid: UUID) {
        retroBoardRepository.deleteById(uuid)
    }

    fun findAllCardsFromRetroBoard(uuid: UUID): Iterable<Card> {
        return findById(uuid).cards.values
    }

    fun addCardToRetroBoard(uuid: UUID, card: Card): Card {
        val retroBoard: RetroBoard = findById(uuid)
        if (card.id == null) {
            card.id = UUID.randomUUID()
        }
        retroBoard.cards[card.id!!] = card
        save(retroBoard)
        return card
    }

    fun findCardByUUID(uuid: UUID, uuidCard: UUID): Card? {
        val retroBoard: RetroBoard = findById(uuid)
        return retroBoard.cards[uuidCard]
    }

    fun saveCard(uuid: UUID, card: Card): Card {
        val retroBoard: RetroBoard = findById(uuid)
        retroBoard.cards[card.id!!] = card
        save(retroBoard)
        return card
    }

    fun removeCardByUUID(uuid: UUID, cardUUID: UUID) {
        val retroBoard: RetroBoard = findById(uuid)
        retroBoard.cards.remove(cardUUID)
        save(retroBoard)
    }
}
