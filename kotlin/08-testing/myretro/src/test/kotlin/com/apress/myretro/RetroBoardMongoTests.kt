package com.apress.myretro

import com.apress.myretro.board.RetroBoard
import com.apress.myretro.persistence.RetroBoardRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import reactor.test.StepVerifier
import java.util.*

@ActiveProfiles("mongoTest")
@DataMongoTest
class RetroBoardMongoTests {
    @Autowired
    lateinit var retroBoardRepository: RetroBoardRepository

    @Test
    fun saveRetroTest() {
        val namex = "Spring Boot 3 Retro"
        val retroBoard = RetroBoard().apply {
            id = UUID.randomUUID()
            name = namex
        }
        val retroBoardResult = retroBoardRepository.insert(retroBoard).block()
        assertThat(retroBoardResult).isNotNull()
        assertThat(retroBoardResult!!.id).isInstanceOf(UUID::class.java)
        assertThat(retroBoardResult.name).isEqualTo(namex)
    }

    @Test
    fun findRetroBoardById() {
        val retroBoard = RetroBoard().apply {
            id = UUID.randomUUID()
            name = "Migration Retro"
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
        var mongoDBContainer = MongoDBContainer("mongo:latest")

        init {
            mongoDBContainer.start()
        }

        @DynamicPropertySource
        @JvmStatic
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri") { mongoDBContainer.replicaSetUrl }
        }
    }
}
