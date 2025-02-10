package com.apress.myretro.config

import com.apress.myretro.advice.RetroBoardAdvice
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
        return ApplicationListener<ApplicationReadyEvent> { _: ApplicationReadyEvent ->
            LOG.info("Application Ready Event")
            val retroBoardId = UUID.fromString("9dc9b71b-a07e-418b-b972-40225449aff2")
            retroBoardService.save(
                RetroBoard(
                    id=retroBoardId,
                    name="Spring Boot Conference",
                    cards = mutableListOf(
                        Card(id=UUID.fromString("bb2a80a5-a0f5-4180-a6dc-80c84bc014c9"),
                            comment="Spring Boot Rocks!", cardType= CardType.HAPPY),
                        Card(id=UUID.randomUUID(),
                            comment="Meet everyone in person", cardType= CardType.HAPPY),
                        Card(id=UUID.randomUUID(),
                            comment="When is the next one?", cardType= CardType.MEH),
                        Card(id=UUID.randomUUID(),
                            comment="Not enough time to talk to everyone", cardType= CardType.SAD)
                    )
                )
            ).subscribe()
        }
    }
    companion object {
        val LOG = LoggerFactory.getLogger(MyRetroConfiguration::class.java)
    }
}
