package com.apress.users.kafka

import com.apress.users.events.UserEvent
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class KUserLogs {
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, UserEvent>

    fun send(topicName: String, key: String, value: UserEvent?) {
        val future: CompletableFuture<SendResult<String, UserEvent>> = kafkaTemplate.send(topicName, key, value)
        future.whenComplete{ sendResult: SendResult<String, UserEvent>, exception: Throwable? ->
            if (exception != null) {
                future.completeExceptionally(exception)
            } else {
                future.complete(sendResult)
            }
            LOG.info("User sent to Kafka topic : {}", value)
        }
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(KUserLogs::class.java)
    }
}
