package com.apress.myretro.web

import com.apress.myretro.board.RetroBoard
import com.apress.myretro.rsocket.UserClient
import com.apress.myretro.service.RetroBoardAndCardService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI
import java.util.*
import java.util.function.Consumer
import java.util.function.Function

@RestController
@RequestMapping("/retros")
class RetroBoardController {
    @Autowired
    private lateinit var retroBoardAndCardService: RetroBoardAndCardService

    @Autowired
    private lateinit var userClient: UserClient

    @get:GetMapping
    val allRetroBoards: Flux<RetroBoard>
        get() = retroBoardAndCardService.findAllRetroBoards()

    @GetMapping("/{uuid}")
    fun findRetroBoardById(@PathVariable uuid: UUID): Mono<ResponseEntity<RetroBoard>> {
        val retroBoardMono = retroBoardAndCardService.findRetroBoardById(uuid)
        return retroBoardMono.map{ body: RetroBoard ->
            ResponseEntity.ok(body)
        }.defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @RequestMapping(method = [RequestMethod.POST, RequestMethod.PUT])
    fun saveRetroBoard(
        @RequestBody @Valid @NotNull retroBoard:RetroBoard,
        componentsBuilder: UriComponentsBuilder
    ): Mono<ResponseEntity<RetroBoard>> {
        val retroBoardMono: Mono<RetroBoard> = retroBoardAndCardService.saveRetroBoard(retroBoard)
        val uri: URI = componentsBuilder.path("/{uuid}")
            .buildAndExpand(retroBoardMono.doOnNext { result: RetroBoard -> result.retroBoardId }
                .subscribe()
            ).toUri()
        return retroBoardMono.map { _: RetroBoard ->
            ResponseEntity.created(uri).build()
        }
    }

    @DeleteMapping("/{uuid}")
    fun deleteRetroBoardById(@PathVariable uuid: UUID): Mono<ResponseEntity<Void>> =
        retroBoardAndCardService.deleteRetroBoardById(uuid).let {
            Mono.just(ResponseEntity<Void>(HttpStatus.NO_CONTENT))
        }
}
