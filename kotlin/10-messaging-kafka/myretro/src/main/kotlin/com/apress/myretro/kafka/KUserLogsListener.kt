package com.apress.myretro.kafka

import com.apress.myretro.events.RetroBoardLog
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KUserLogsListener {
    @KafkaListener(topics = [TOPIC], groupId = GROUP_ID)
    fun userLogs(userEvent: UserEvent) {
        LOG.info("User logs: {}", userEvent)
    }

    companion object {
        private const val TOPIC = "user-logs"
        private const val GROUP_ID = "my-retro"
        private val LOG = LoggerFactory.getLogger(RetroBoardLog::class.java)
    }
}
