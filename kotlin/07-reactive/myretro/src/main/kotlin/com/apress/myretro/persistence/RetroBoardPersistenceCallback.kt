package com.apress.myretro.persistence

import com.apress.myretro.board.RetroBoard
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.mapping.event.ReactiveBeforeConvertCallback
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Component
class RetroBoardPersistenceCallback : ReactiveBeforeConvertCallback<RetroBoard> {
    override fun onBeforeConvert(entity: RetroBoard, collection: String): Publisher<RetroBoard> {
        with(entity) {
            id = id ?: UUID.randomUUID()
            cards = cards ?: mutableListOf()
            LOG.info("[CALLBACK] onBeforeConvert {}", this)
        }
        return Mono.just(entity)
    }
    companion object {
        val LOG = LoggerFactory.getLogger(RetroBoardPersistenceCallback::class.java)
    }
}
