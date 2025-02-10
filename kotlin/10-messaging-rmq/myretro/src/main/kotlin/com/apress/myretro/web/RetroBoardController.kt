package com.apress.myretro.web

import com.apress.myretro.board.Card
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.service.RetroBoardAndCardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/retros")
class RetroBoardController {
    @Autowired
    private lateinit var retroBoardAndCardService: RetroBoardAndCardService

    @get:GetMapping
    val allRetroBoards: ResponseEntity<Iterable<RetroBoard>>
        get() = ResponseEntity.ok(retroBoardAndCardService.allRetroBoards)

    @GetMapping("/{uuid}/cards")
    fun getAllCardsFromBoard(@PathVariable uuid: UUID): ResponseEntity<Iterable<Card>>? {
        return null
    }
}
