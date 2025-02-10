package com.apress.myretro.service

import com.apress.myretro.board.Card
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.exception.CardNotFoundException
import com.apress.myretro.exception.RetroBoardNotFoundException
import com.apress.myretro.persistence.CardRepository
import com.apress.myretro.persistence.RetroBoardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Supplier
import kotlin.jvm.optionals.getOrNull

@Service
class RetroBoardService {
    @Autowired
    lateinit var retroBoardRepository: RetroBoardRepository

    @Autowired
    lateinit var cardRepository: CardRepository

    fun save(domain: RetroBoard): RetroBoard {
        return retroBoardRepository.save<RetroBoard>(domain)
    }

    fun findById(uuid: UUID): RetroBoard =
        retroBoardRepository.findById(uuid).get()

    fun findAll(): Iterable<RetroBoard> =
        retroBoardRepository.findAll()

    fun delete(uuid: UUID) {
        retroBoardRepository.deleteById(uuid)
    }

    fun findAllCardsFromRetroBoard(uuid: UUID): Iterable<Card> =
        findById(uuid).cards

    fun addCardToRetroBoard(uuid: UUID, card: Card): Card {
        return card.apply {
            retroBoardRepository.findById(uuid).getOrNull()?.also {
                this.retroBoard = it
            }?:throw RetroBoardNotFoundException()
            cardRepository.save<Card>(this)
        }
    }

    fun addMultipleCardsToRetroBoard(uuid: UUID, cards: Collection<Card>) {
        val retroBoard: RetroBoard = findById(uuid)
        cards.forEach{ card: Card -> card.retroBoard = retroBoard }
        cardRepository.saveAll(cards)
    }

    fun findCardByUUID(uuidCard: UUID): Card =
        cardRepository.findById(uuidCard).getOrNull()?:throw CardNotFoundException()

    fun saveCard(card: Card): Card {
        return cardRepository.save<Card>(card)
    }

    fun removeCardByUUID(cardUUID: UUID) {
        cardRepository.deleteById(cardUUID)
    }
}
