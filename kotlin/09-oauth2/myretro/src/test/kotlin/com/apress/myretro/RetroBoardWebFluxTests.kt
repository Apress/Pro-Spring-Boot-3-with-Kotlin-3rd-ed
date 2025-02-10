package com.apress.myretro

import com.apress.myretro.board.Card
import com.apress.myretro.board.CardType
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.client.UserClient
import com.apress.myretro.security.RetroBoardSecurityConfig
import com.apress.myretro.service.RetroBoardService
import com.apress.myretro.web.RetroBoardController
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Import(RetroBoardSecurityConfig::class)
@WithMockUser
@WebFluxTest(controllers = [RetroBoardController::class])
class RetroBoardWebFluxTests {
    @MockBean
    private lateinit var retroBoardService: RetroBoardService

    @MockBean
    private lateinit var userClient: UserClient

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun allRetroBoardTest() {
        fail("Not yet implemented")
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
        fail("Not yet implemented")
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
    @WithMockUser(roles = ["ADMIN"])
    fun saveRetroBoardTest() {
        fail("Not yet implemented")
        val retroBoard = RetroBoard().apply {
            name = "Simple Retro"
        }
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
    @WithMockUser(roles = ["ADMIN"])
    fun deleteRetroBoardTest() {
        fail("Not yet implemented")
        val uuid = UUID.randomUUID()
        Mockito.`when`(retroBoardService.delete(uuid)).thenReturn(Mono.empty())
        webClient.delete()
            .uri("/retros/{uuid}", uuid.toString())
            .exchange()
            .expectStatus().isOk()
        Mockito.verify(retroBoardService, Mockito.times(1)).delete(uuid)
    }
}
