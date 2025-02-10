package com.apress.myretro.config

import com.apress.myretro.board.Card
import com.apress.myretro.board.CardType
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.service.RetroBoardService
import org.slf4j.LoggerFactory
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
        return ApplicationListener<ApplicationReadyEvent> { _: ApplicationReadyEvent? ->
            LOG.info("Application Ready Event")
            val retroBoardId = UUID.fromString("9dc9b71b-a07e-418b-b972-40225449aff2")
            retroBoardService.save(
                RetroBoard(
                    retroBoardId,
                    "Spring Boot Conference 2023",
                    mutableListOf(
                        Card(UUID.fromString("bb2a80a5-a0f5-4180-a6dc-80c84bc014c9"),
                            "Spring Boot Rocks!", CardType.HAPPY),
                        Card(UUID.randomUUID(),
                            "Meet everyone in person",CardType.HAPPY),
                        Card(UUID.randomUUID(),
                            "When is the next one?",CardType.MEH),
                        Card(UUID.randomUUID(),
                            "Not enough time to talk to everyone", CardType.SAD)
                    )
                )
            ).subscribe()
        }
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(MyRetroConfiguration::class.java)
    }
}
