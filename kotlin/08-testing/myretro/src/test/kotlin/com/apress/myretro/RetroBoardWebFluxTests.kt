package com.apress.myretro

import com.apress.myretro.board.Card
import com.apress.myretro.board.CardType
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.service.RetroBoardService
import com.apress.myretro.web.RetroBoardController
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@WebFluxTest(controllers = [RetroBoardController::class])
class RetroBoardWebFluxTests {
    @MockBean
    lateinit var retroBoardService: RetroBoardService

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun allRetroBoardTest() {
            Mockito.`when`(retroBoardService.findAll()).thenReturn(
                Flux.just(
                    RetroBoard(
                        UUID.randomUUID(), "Simple Retro", mutableListOf(
                            Card(UUID.randomUUID(), "Happy to be here", CardType.HAPPY),
                            Card(UUID.randomUUID(), "Meetings everywhere", CardType.SAD),
                            Card(UUID.randomUUID(), "Vacations?", CardType.MEH),
                            Card(UUID.randomUUID(), "Awesome Discounts", CardType.HAPPY),
                            Card(UUID.randomUUID(), "Missed my train", CardType.SAD)
                        )
                    )
                )
            )
            webClient.get()
                .uri("/retros")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$[0].name").isEqualTo("Simple Retro")
            Mockito.verify(retroBoardService, Mockito.times(1)).findAll()
        }

    @Test
    fun findRetroBoardByIdTest() {
        val uuid = UUID.randomUUID()
        Mockito.`when`(retroBoardService.findById(uuid)).thenReturn(
            Mono.just(
                RetroBoard(
                    uuid, "Simple Retro", mutableListOf(
                        Card(UUID.randomUUID(), "Happy to be here", CardType.HAPPY),
                        Card(UUID.randomUUID(), "Meetings everywhere", CardType.SAD),
                        Card(UUID.randomUUID(), "Vacations?", CardType.MEH),
                        Card(UUID.randomUUID(), "Awesome Discounts", CardType.HAPPY),
                        Card(UUID.randomUUID(), "Missed my train", CardType.SAD)
                    )
                )
            )
        )
        webClient.get()
            .uri("/retros/{uuid}", uuid.toString())
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBody(RetroBoard::class.java)
        Mockito.verify(retroBoardService, Mockito.times(1)).findById(uuid)
    }

    @Test
    fun saveRetroBoardTest() {
        val retroBoard = RetroBoard()
        retroBoard.name = "Simple Retro"
        Mockito.`when`(retroBoardService.save(retroBoard))
            .thenReturn(Mono.just(retroBoard))
        webClient.post()
            .uri("/retros")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(retroBoard))
            .exchange()
            .expectStatus().isOk()
        Mockito.verify(retroBoardService, Mockito.times(1)).save(retroBoard)
    }

    @Test
    fun deleteRetroBoardTest() {
        val uuid = UUID.randomUUID()
        Mockito.`when`(retroBoardService.delete(uuid)).thenReturn(Mono.empty())
        webClient.delete()
            .uri("/retros/{uuid}", uuid.toString())
            .exchange()
            .expectStatus().isOk()
        Mockito.verify(retroBoardService, Mockito.times(1)).delete(uuid)
    }
}
