package com.apress.myretro.config

import com.apress.myretro.board.Card
import com.apress.myretro.board.CardType
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.service.RetroBoardAndCardService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@EnableConfigurationProperties(RetroBoardProperties::class)
@Configuration
class RetroBoardConfig {
    @Bean
    fun init(service: RetroBoardAndCardService): CommandLineRunner {
        return CommandLineRunner { _: Array<String> ->
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
            service.saveOrUpdateRetroBoard(retroBoard)
        }
    }
}
