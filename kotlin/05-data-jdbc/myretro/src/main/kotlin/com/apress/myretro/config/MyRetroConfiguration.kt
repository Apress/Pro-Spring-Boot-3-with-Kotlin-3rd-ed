package com.apress.myretro.config

import com.apress.myretro.board.Card
import com.apress.myretro.board.CardType
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.service.RetroBoardService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(MyRetroProperties::class)
@Configuration
class MyRetroConfiguration {
    @Bean
    fun ready(retroBoardService: RetroBoardService): ApplicationListener<ApplicationReadyEvent> {
        return ApplicationListener<ApplicationReadyEvent> { _: ApplicationReadyEvent ->
            val id = retroBoardService.save(
                RetroBoard(name = "Spring Boot Conference")
            ).id!!
            with(retroBoardService) {
                addCardToRetroBoard(
                    id, Card(comment = "Spring Boot Rocks!", cardType = CardType.HAPPY, retroBoardId = id)
                )
                addCardToRetroBoard(
                    id, Card(comment = "Meet everyone in person", cardType = CardType.HAPPY, retroBoardId = id)
                )
                addCardToRetroBoard(
                    id, Card(comment = "When is the next one?", cardType = CardType.MEH, retroBoardId = id)
                )
                addCardToRetroBoard(
                    id, Card(comment = "Not enough time to talk to everyone", cardType = CardType.SAD, retroBoardId = id)
                )
            }
        }
    }
}
