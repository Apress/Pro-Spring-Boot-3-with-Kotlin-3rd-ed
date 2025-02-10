package com.apress.myretro.web

import com.apress.myretro.board.Card
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.service.RetroBoardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/retros")
class RetroBoardController {
    @Autowired
    private lateinit var retroBoardService: RetroBoardService

    @get:GetMapping
    val allRetroBoards: Flux<RetroBoard>
        get() = retroBoardService.findAll()

    @PostMapping
    fun saveRetroBoard(@RequestBody retroBoard: RetroBoard): Mono<RetroBoard> {
        return retroBoardService.save(retroBoard)
    }

    @DeleteMapping("/{uuid}")
    fun deleteRetroBoard(@PathVariable uuid: UUID): Mono<Void> {
        retroBoardService.delete(uuid)
        return Mono.empty()
    }

    @GetMapping("/{uuid}")
    fun findRetroBoardById(@PathVariable uuid: UUID): Mono<RetroBoard> {
        return retroBoardService.findById(uuid)
    }

    @GetMapping("/{uuid}/cards")
    fun getAllCardsFromBoard(@PathVariable uuid: UUID): Flux<Card> {
        return retroBoardService.findAllCardsFromRetroBoard(uuid)
    }

    @PutMapping("/{uuid}/cards")
    fun addCardToRetroBoard(@PathVariable uuid: UUID, @RequestBody card: Card): Mono<Card> {
        return retroBoardService.addCardToRetroBoard(uuid, card)
    }

    @GetMapping("/cards/{uuidCard}")
    fun getCardByUUID(@PathVariable uuidCard: UUID): Mono<Card> {
        return retroBoardService.findCardByUUID(uuidCard)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}/cards/{uuidCard}")
    fun deleteCardFromRetroBoard(@PathVariable uuid: UUID, @PathVariable uuidCard: UUID): Mono<Void> {
        return retroBoardService.removeCardByUUID(uuid, uuidCard)
    }
}
