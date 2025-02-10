package com.apress.myretro.service

import com.apress.myretro.board.Card
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.exception.CardNotFoundException
import com.apress.myretro.persistence.RetroBoardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class RetroBoardService {
    @Autowired
    lateinit var retroBoardRepository: RetroBoardRepository

    fun save(domain: RetroBoard): RetroBoard =
        domain.also { it.cards = it.cards ?: mutableListOf() } .run {
            retroBoardRepository.save<RetroBoard>(this)
        }

    fun findById(uuid: UUID): RetroBoard =
        retroBoardRepository.findById(uuid).get()

    fun findAll(): Iterable<RetroBoard> =
        retroBoardRepository.findAll()

    fun delete(uuid: UUID) =
        retroBoardRepository.deleteById(uuid)

    fun findAllCardsFromRetroBoard(uuid: UUID): Iterable<Card> =
        findById(uuid).cards ?: emptyList()

    fun addCardToRetroBoard(uuid: UUID, card: Card): Card {
        val retroBoard: RetroBoard = retroBoardRepository.findById(uuid).get()
        val cards: MutableList<Card> = retroBoard.cards ?: mutableListOf()
        card.id = card.id ?: UUID.randomUUID()
        cards.add(card)
        retroBoard.cards = cards
        retroBoardRepository.save<RetroBoard>(retroBoard)
        return card
    }

    fun findCardByUUID(uuid: UUID, uuidCard: UUID): Card =
        findRetroBoardAndCard(uuid, uuidCard).second

    fun saveCard(uuid: UUID, card: Card): Card {
        val (retroBoard, cardToSave) = findRetroBoardAndCard(uuid, card.id!!)
        cardToSave.comment = card.comment
        cardToSave.cardType = card.cardType
        retroBoardRepository.save<RetroBoard>(retroBoard)
        return cardToSave
    }

    fun removeCardByUUID(uuid: UUID, cardUUID: UUID) {
        val helper = findRetroBoardAndCard(uuid, cardUUID)
        helper.first.cards?.remove(helper.second)
        retroBoardRepository.save(helper.first)
    }

    private fun findRetroBoardAndCard(uuid: UUID, cardUUID: UUID): Pair<RetroBoard, Card> {
        val retroBoard: RetroBoard = retroBoardRepository.findById(uuid).get()
        val card = retroBoard.cards?.firstOrNull() { c: Card -> c.id == cardUUID } ?:
            throw CardNotFoundException()
        return Pair(retroBoard, card)
    }
}
