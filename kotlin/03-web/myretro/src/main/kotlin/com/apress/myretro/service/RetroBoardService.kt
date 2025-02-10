package com.apress.myretro.service

import com.apress.myretro.board.Card
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.exception.CardNotFoundException
import com.apress.myretro.persistence.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class RetroBoardService {
    @Autowired
    lateinit var repository: Repository<RetroBoard, UUID>

    fun save(domain: RetroBoard): RetroBoard? {
        //if (domain.cards == null) domain.cards = mutableListOf<Card>()
        return repository.save(domain)
    }

    fun findById(uuid: UUID): RetroBoard = repository.findById(uuid)

    fun findAll(): Iterable<RetroBoard> = repository.findAll()

    fun delete(uuid: UUID) {
        repository.delete(uuid)
    }

    fun findAllCardsFromRetroBoard(uuid: UUID): Iterable<Card> =
        findById(uuid).cards.asIterable()

    fun addCardToRetroBoard(uuid: UUID, card: Card): Card {
        card.id = card.id ?: UUID.randomUUID()
        val retroBoard: RetroBoard = findById(uuid)
        retroBoard.cards = retroBoard.cards.toMutableList().apply { add(card) }
        return card
    }

    fun findCardByUUIDFromRetroBoard(uuid: UUID, uuidCard: UUID?): Card =
        findById(uuid).cards.filter { c -> c.id == uuidCard }.firstOrNull()?:throw CardNotFoundException()

    fun removeCardFromRetroBoard(uuid: UUID, cardUUID: UUID) {
        val retroBoard: RetroBoard = findById(uuid)
        val cardList: MutableList<Card> = retroBoard.cards.toMutableList()
        cardList.removeIf { card: Card -> card.id!! == cardUUID }
        retroBoard.cards = cardList
    }
}
