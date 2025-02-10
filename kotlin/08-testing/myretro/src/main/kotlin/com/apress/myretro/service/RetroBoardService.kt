package com.apress.myretro.service

import com.apress.myretro.board.Card
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.persistence.RetroBoardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import java.util.function.Function
import java.util.function.Predicate

@Service
class RetroBoardService {
    @Autowired
    lateinit var retroBoardRepository: RetroBoardRepository

    fun save(domain: RetroBoard): Mono<RetroBoard> {
        return retroBoardRepository.save(domain)
    }

    fun findById(uuid: UUID): Mono<RetroBoard> {
        return retroBoardRepository.findById(uuid)
    }

    fun findAll(): Flux<RetroBoard> {
        return retroBoardRepository.findAll()
    }

    fun delete(uuid: UUID): Mono<Void> {
        return retroBoardRepository.deleteById(uuid)
    }

    fun findAllCardsFromRetroBoard(uuid: UUID): Flux<Card> {
        return findById(uuid).flatMapIterable(RetroBoard::cards)
    }

    fun addCardToRetroBoard(uuid: UUID, card: Card): Mono<Card> {
        return findById(uuid).flatMap<Card> { retroBoard: RetroBoard ->
            card.id = card.id ?: UUID.randomUUID()
            retroBoard.cards!!.add(card)
            save(retroBoard).thenReturn<Card>(card)
        }
    }

    fun findCardByUUID(uuidCard: UUID): Mono<Card> {
        val result: Mono<RetroBoard> = retroBoardRepository.findRetroBoardByIdAndCardId(uuidCard)
        return result.flatMapIterable(RetroBoard::cards)
            .filter{ card -> card.id == uuidCard }.next()
    }

    fun removeCardByUUID(uuid: UUID, cardUUID: UUID): Mono<Void> {
        findById(uuid)
            .doOnNext { retroBoard: RetroBoard ->
                retroBoard.cards!!.removeIf { card -> card.id == cardUUID }
            }
            .flatMap { entity: RetroBoard -> save(entity) }
            .subscribe()
        return Mono.empty()
    }
}
