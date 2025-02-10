package com.apress.myretro

import com.apress.myretro.board.Card
import com.apress.myretro.board.CardType
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.exception.CardNotFoundException
import com.apress.myretro.exception.RetroBoardNotFoundException
import com.apress.myretro.service.RetroBoardService
import org.assertj.core.api.Assertions
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
internal class MyretroApplicationTests {
    @Autowired
    lateinit var service: RetroBoardService

    var retroBoardUUID = UUID.fromString("9DC9B71B-A07E-418B-B972-40225449AFF2")
    var cardUUID = UUID.fromString("BB2A80A5-A0F5-4180-A6DC-80C84BC014C9")
    var mehCardUUID = UUID.fromString("775A3905-D6BE-49AB-A3C4-EBE287B51539")

    @Test
    fun saveRetroBoardTest() {
        val retroBoard = service.save(RetroBoard(name = "Gathering 2023"))
        Assertions.assertThat(retroBoard).isNotNull()
        Assertions.assertThat(retroBoard!!.id).isNotNull()
    }

    @Test
    fun findAllRetroBoardsTest() {
        val retroBoards = service.findAll()
        Assertions.assertThat(retroBoards).isNotNull()
        Assertions.assertThat(retroBoards).isNotEmpty()
    }

    @Test
    fun cardsRetroBoardNotFoundTest() {
        AssertionsForClassTypes.assertThatThrownBy { service.findAllCardsFromRetroBoard(UUID.randomUUID()) }
            .isInstanceOf(
                RetroBoardNotFoundException::class.java
            )
    }

    @Test
    fun findRetroBoardTest() {
        val retroBoard = service.findById(retroBoardUUID)
        Assertions.assertThat(retroBoard).isNotNull()
        Assertions.assertThat(retroBoard!!.name).isEqualTo("Spring Boot 3.0 Meeting")
        Assertions.assertThat(retroBoard.id).isEqualTo(retroBoardUUID)
    }

    @Test
    fun findCardsInRetroBoardTest() {
        val retroBoard = service.findById(retroBoardUUID)
        Assertions.assertThat(retroBoard).isNotNull()
        Assertions.assertThat(retroBoard!!.cards).isNotEmpty()
    }

    @Test
    fun addCardToRetroBoardTest() {
        val card = service.addCardToRetroBoard(
            retroBoardUUID, Card(
                comment = "Amazing session",
                cardType = CardType.HAPPY)
        )
        Assertions.assertThat(card).isNotNull()
        Assertions.assertThat(card.id).isNotNull()
        val retroBoard = service.findById(retroBoardUUID)
        Assertions.assertThat(retroBoard).isNotNull()
        Assertions.assertThat(retroBoard!!.cards).isNotEmpty()
    }

    @Test
    fun findAllCardsFromRetroBoardTest() {
        val cardList = service.findAllCardsFromRetroBoard(retroBoardUUID)
        Assertions.assertThat(cardList).isNotNull()
        Assertions.assertThat((cardList as Collection<*>).size).isGreaterThan(3)
    }

    @Test
    fun removeCardsFromRetroBoardTest() {
        service.removeCardFromRetroBoard(retroBoardUUID, cardUUID)
        val retroBoard = service.findById(retroBoardUUID)
        Assertions.assertThat(retroBoard).isNotNull()
        Assertions.assertThat(retroBoard!!.cards).isNotEmpty()
        Assertions.assertThat(retroBoard.cards).hasSizeLessThan(4)
    }

    @Test
    fun findCardByIdInRetroBoardTest() {
        val card = service.findCardByUUIDFromRetroBoard(retroBoardUUID, mehCardUUID)
        Assertions.assertThat(card).isNotNull()
        Assertions.assertThat(card.id).isEqualTo(mehCardUUID)
    }

    @Test
    fun notFoundCardInRetroBoardTest() {
        AssertionsForClassTypes.assertThatThrownBy {
            service.findCardByUUIDFromRetroBoard(
                retroBoardUUID,
                UUID.randomUUID()
            )
        }.isInstanceOf( CardNotFoundException::class.java )
    }
}
