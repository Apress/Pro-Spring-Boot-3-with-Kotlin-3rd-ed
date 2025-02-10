package com.apress.myretro.config

import com.apress.myretro.board.Card
import com.apress.myretro.board.CardType
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.service.RetroBoardAndCardService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class RetroBoardConfig {
    @Bean
    fun initRetro(service: RetroBoardAndCardService): CommandLineRunner {
        return CommandLineRunner { args: Array<String?>? ->
            val retroBoard = RetroBoard().apply {
                name = "Spring Boot 3 Retro"
                cards = listOf(
                    Card().apply {
                        comment = "Nice to meet everybody"
                        cardType = CardType.HAPPY
                    },
                    Card().apply {
                        comment = "When are we going to travel?"
                        cardType = CardType.MEH
                    },
                    Card().apply {
                        comment = "When are we going to travel?"
                        cardType = CardType.SAD
                    }
                )
            }
            val (retroBoardId, name, cards, created, modified) = service.saveOrUpdateRetroBoard(retroBoard)
        }
    }
}
