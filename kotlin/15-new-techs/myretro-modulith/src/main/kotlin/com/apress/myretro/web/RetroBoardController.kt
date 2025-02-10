package com.apress.myretro.web

import com.apress.myretro.board.RetroBoard
import com.apress.myretro.service.RetroBoardAndCardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/retros")
class RetroBoardController {
    @Autowired
    private lateinit var retroBoardAndCardService: RetroBoardAndCardService

    @get:GetMapping
    val allRetroBoards: ResponseEntity<Iterable<RetroBoard>>
        get() = ResponseEntity.ok(retroBoardAndCardService.allRetroBoards)

    @GetMapping("/{uuid}")
    fun findRetroBoardById(@PathVariable uuid: UUID): ResponseEntity<RetroBoard> {
        return ResponseEntity.ok(retroBoardAndCardService.findRetroBoardById(uuid))
    }

    @RequestMapping(method = [RequestMethod.POST, RequestMethod.PUT])
    fun saveRetroBoard(
        @RequestBody retroBoard: RetroBoard,
        componentsBuilder: UriComponentsBuilder
    ): ResponseEntity<RetroBoard> {
        val saveOrUpdateRetroBoard: RetroBoard = retroBoardAndCardService.saveOrUpdateRetroBoard(retroBoard)
        val uri: URI = componentsBuilder.path("/{uuid}").buildAndExpand(saveOrUpdateRetroBoard.retroBoardId).toUri()
        return ResponseEntity.created(uri).body(saveOrUpdateRetroBoard)
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteRetroBoardById(@PathVariable uuid: UUID): ResponseEntity<*> {
        retroBoardAndCardService.deleteRetroBoardById(uuid)
        return ResponseEntity.noContent().build<Any>()
    }
}
