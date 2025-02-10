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

@Service
class RetroBoardService {
    @Autowired
    lateinit var retroBoardRepository: RetroBoardRepository
    @Autowired
    lateinit var cardRepository: CardRepository

    fun save(domain: RetroBoard): RetroBoard {
        domain.cards = domain.cards ?: mutableListOf()
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
        return findById(uuid).cards ?: mutableListOf()
    }

    fun addCardToRetroBoard(uuid: UUID, card: Card): Card {
        return retroBoardRepository.findById(uuid).map<Card>{ retroBoard: RetroBoard? ->
                card.retroBoard = retroBoard
                cardRepository.save<Card>(card)
            }
            .orElseThrow{ RetroBoardNotFoundException() }
    }

    fun addMultipleCardsToRetroBoard(uuid: UUID, cards: List<Card>) {
        val retroBoard: RetroBoard = findById(uuid)
        cards.forEach { card: Card -> card.retroBoard = retroBoard }
        cardRepository.saveAll(cards)
    }

    fun findCardByUUID(uuidCard: UUID): Card {
        val result: Optional<Card> = cardRepository.findById(uuidCard)
        return if (result.isPresent) {
            result.get()
        } else {
            throw CardNotFoundException()
        }
    }

    fun saveCard(card: Card): Card {
        return cardRepository.save<Card>(card)
    }

    fun removeCardByUUID(cardUUID: UUID) {
        cardRepository.deleteById(cardUUID)
    }
}
