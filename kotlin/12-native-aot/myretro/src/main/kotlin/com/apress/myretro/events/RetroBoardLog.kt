package com.apress.myretro.events

import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class RetroBoardLog {
    @Async
    @EventListener
    fun retroBoardEventHandler(event: RetroBoardEvent) {
        LOG.info(
            "EVENT:::RetroBoard[{}] Action[{}] happen at: {}",
            event.retroBoardId,
            event.action,
            event.happenAt
        )
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(RetroBoardLog::class.java)
    }
}
