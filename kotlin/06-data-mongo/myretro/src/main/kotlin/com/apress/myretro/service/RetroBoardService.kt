package com.apress.myretro.service

import com.apress.myretro.board.Card
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.exception.CardNotFoundException
import com.apress.myretro.persistence.RetroBoardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class RetroBoardService {
    @Autowired
    lateinit var retroBoardRepository: RetroBoardRepository

    fun save(domain: RetroBoard): RetroBoard =
        domain.also { it.cards = it.cards ?: mutableListOf() }.run {
            retroBoardRepository.save<RetroBoard>(this)
        }

    fun findById(uuid: UUID): RetroBoard =
        retroBoardRepository.findById(uuid).get()

    fun findAll(): Iterable<RetroBoard> =
        retroBoardRepository.findAll()

    fun delete(uuid: UUID) =
        retroBoardRepository.deleteById(uuid)

    fun findAllCardsFromRetroBoard(uuid: UUID): Iterable<Card> =
        findById(uuid).cards!!

    fun addCardToRetroBoard(uuid: UUID, card: Card): Card {
        if (card.id == null) card.id = UUID.randomUUID()
        val retroBoard: RetroBoard = findById(uuid)
        retroBoard.addCard(card)
        retroBoardRepository.save<RetroBoard>(retroBoard)
        return card
    }

    fun addMultipleCardsToRetroBoard(uuid: UUID, cards: List<Card>) {
        val retroBoard: RetroBoard = findById(uuid)
        retroBoard.addCards(cards)
        retroBoardRepository.save<RetroBoard>(retroBoard)
    }

    fun findCardByUUID(uuidCard: UUID): Card {
        val result: Optional<RetroBoard> = retroBoardRepository.findRetroBoardByCardId(uuidCard)
        return result.getOrNull()?.cards?.firstOrNull{ it.id == uuidCard }  ?:
            throw CardNotFoundException()
    }

    fun removeCardByUUID(uuid: UUID, cardUUID: UUID) {
        val retroBoard: RetroBoard = findById(uuid)
        retroBoard.removeCard(cardUUID)
        retroBoardRepository.save<RetroBoard>(retroBoard)
    }
}
