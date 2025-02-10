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
import java.util.*

@EnableConfigurationProperties(MyRetroProperties::class)
@Configuration
class MyRetroConfiguration {
    @Bean
    fun ready(retroBoardService: RetroBoardService): ApplicationListener<ApplicationReadyEvent> {
        return ApplicationListener<ApplicationReadyEvent> { applicationReadyEvent: ApplicationReadyEvent? ->
            val retroBoardId = UUID.fromString("9dc9b71b-a07e-418b-b972-40225449aff2")
            val retroBoard: RetroBoard = RetroBoard(
                id = retroBoardId,
                name = "Spring Boot Conference",
                cards = mutableMapOf(
                    UUID.fromString("bb2a80a5-a0f5-4180-a6dc-80c84bc014c9") to
                    Card(id = UUID.fromString("bb2a80a5-a0f5-4180-a6dc-80c84bc014c9"),
                        comment = "Spring Boot Rocks!", cardType = CardType.HAPPY),
                    UUID.fromString("f9de7f11-5393-4b5b-8e9d-10eca5f50189") to
                    Card(id = UUID.fromString("f9de7f11-5393-4b5b-8e9d-10eca5f50189"),
                        comment = "Meet everyone in person", cardType = CardType.HAPPY),
                    UUID.fromString("6cdb30d6-43f2-42b7-b0db-f3acbc53d467") to
                    Card(id = UUID.fromString("6cdb30d6-43f2-42b7-b0db-f3acbc53d467"),
                        comment = "When is the next one?", cardType = CardType.MEH),
                    UUID.fromString("9de1f7f9-2470-4c8d-86f2-371203620fcd") to
                    Card(id = UUID.fromString("9de1f7f9-2470-4c8d-86f2-371203620fcd"),
                        comment = "Not enough time to talk to everyone", cardType = CardType.SAD)
                )
            )
            retroBoardService.save(retroBoard)
        }
    }
}
