package com.apress.myretro

import com.apress.myretro.board.RetroBoard
import com.apress.myretro.persistence.RetroBoardRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.test.StepVerifier
import java.util.*

@Testcontainers
@DataMongoTest
class RetroBoardMongoTests {
    @Autowired
    private lateinit var retroBoardRepository: RetroBoardRepository
    
    @Test
    fun saveRetroTest() {
        val retroBoard = RetroBoard().apply {
            id = UUID.randomUUID()
            name = "Spring Boot 3 Retro"
            cards = mutableListOf()
        }
        val retroBoardResult = retroBoardRepository.insert(retroBoard).block()
        assertThat(retroBoardResult).isNotNull()
        assertThat(retroBoardResult!!.id).isInstanceOf(UUID::class.java)
        assertThat(retroBoardResult.name).isEqualTo("Spring Boot 3 Retro")
    }

    @Test
    fun findRetroBoardById() {
        val retroBoard = RetroBoard().apply {
            id = UUID.randomUUID()
            name = "Migration Retro"
            cards = mutableListOf()
        }
        val retroBoardResult = retroBoardRepository.insert(retroBoard).block()
        assertThat(retroBoardResult).isNotNull()
        assertThat(retroBoardResult!!.id).isInstanceOf(UUID::class.java)
        val retroBoardMono = retroBoardRepository.findById(retroBoardResult.id!!)
        StepVerifier
            .create(retroBoardMono)
            .assertNext { retro: RetroBoard ->
                assertThat(retro).isNotNull()
                assertThat(retro).isInstanceOf(RetroBoard::class.java)
                assertThat(retro.name).isEqualTo("Migration Retro")
            }
            .expectComplete()
            .verify()
    }

    companion object {
        @Container
        @ServiceConnection
        var mongoDBContainer = MongoDBContainer("mongo:latest")
    }
}
