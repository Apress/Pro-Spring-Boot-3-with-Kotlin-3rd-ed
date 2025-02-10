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
import org.springframework.transaction.annotation.Transactional

@EnableConfigurationProperties(MyRetroProperties::class)
@Configuration
class MyRetroConfiguration {
    @Bean
    fun ready(retroBoardService: RetroBoardService): ApplicationListener<ApplicationReadyEvent> {
        return ApplicationListener<ApplicationReadyEvent> { _: ApplicationReadyEvent? ->
            val id = retroBoardService.save(
                RetroBoard(name = "Spring Boot Conference")
            ).id!!
            retroBoardService.addMultipleCardsToRetroBoard(id, setOf(
                    Card(comment = "Spring Boot Rocks!", cardType = CardType.HAPPY),
                    Card(comment = "Meet everyone in person", cardType = CardType.HAPPY),
                    Card(comment = "When is the next one?", cardType = CardType.MEH),
                    Card(comment = "Not enough time to talk to everyone", cardType = CardType.SAD)
            )
            )
        }
    }
}
