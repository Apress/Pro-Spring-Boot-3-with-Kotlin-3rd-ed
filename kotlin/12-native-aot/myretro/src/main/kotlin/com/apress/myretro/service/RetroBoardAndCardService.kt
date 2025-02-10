package com.apress.myretro.service

import com.apress.myretro.board.RetroBoard
import com.apress.myretro.client.UserClient
import com.apress.myretro.client.model.User
import com.apress.myretro.events.RetroBoardEvent
import com.apress.myretro.events.RetroBoardEventAction
import com.apress.myretro.exceptions.RetroBoardNotFoundException
import com.apress.myretro.persistence.RetroBoardRepository
import io.micrometer.core.instrument.Counter
import io.micrometer.observation.annotation.Observed
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
@Observed(name = "retro-board-service", contextualName = "retroBoardAndCardService")
class RetroBoardAndCardService {
    @Autowired
    private lateinit var retroBoardRepository: RetroBoardRepository
    @Autowired
    private lateinit var eventPublisher: ApplicationEventPublisher
    @Autowired
    private lateinit var retroBoardCounter: Counter
    @Autowired
    private lateinit var userClient: UserClient

    // Uncomment this to see the effect of the @Observed annotation (Custom Observation))
    //@Observed(name = "retro-boards",contextualName = "allRetroBoards")
    fun allRetroBoards():List<RetroBoard> {
        LOG.info("Getting all retro boards")
        return retroBoardRepository.findAll()
    }

    // Uncomment this to see the effect of the @Observed annotation (Custom Observation))
    //@Observed(name = "retro-board-id",contextualName = "findRetroBoardById")
    fun findRetroBoardById(uuid: UUID): RetroBoard {
        LOG.info("Getting retro board by id: {}", uuid)
        return retroBoardRepository.findById(uuid)
            .orElseThrow{ RetroBoardNotFoundException() }
    }

    fun saveOrUpdateRetroBoard(retroBoard: RetroBoard): RetroBoard {
        val retroBoardResult: RetroBoard = retroBoardRepository.save<RetroBoard>(retroBoard)
        eventPublisher.publishEvent(
            RetroBoardEvent(
                retroBoardResult.retroBoardId,
                RetroBoardEventAction.CHANGED,
                LocalDateTime.now()
            )
        )
        retroBoardCounter.increment()
        return retroBoardResult
    }

    fun deleteRetroBoardById(uuid: UUID) {
        retroBoardRepository.deleteById(uuid)
        eventPublisher.publishEvent(RetroBoardEvent(uuid, RetroBoardEventAction.DELETED, LocalDateTime.now()))
    }

    //@Observed(name = "users",contextualName = "allUsers")
    fun allUsers() {
        LOG.info("Getting all users")
        userClient.allUsers.subscribe(
            { user: User? -> LOG.info("User: {}", user) },
            { error: Throwable? -> LOG.error("Error: {}", error.toString()) },
            { LOG.info("Completed") }
        )
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(RetroBoardAndCardService::class.java)
    }
}
