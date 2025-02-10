package com.apress.myretro.service

import com.apress.myretro.board.RetroBoard
import com.apress.myretro.events.RetroBoardEvent
import com.apress.myretro.events.RetroBoardEventAction
import com.apress.myretro.exceptions.RetroBoardNotFoundException
import com.apress.myretro.persistence.RetroBoardRepository
import com.apress.users.UserEvent
import io.micrometer.core.instrument.Counter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionalEventListener
import java.time.LocalDateTime
import java.util.*

@Service
class RetroBoardAndCardService {
    @Autowired
    private lateinit var retroBoardRepository: RetroBoardRepository
    @Autowired
    private lateinit var eventPublisher: ApplicationEventPublisher
    @Autowired
    private lateinit var retroBoardCounter: Counter
    @Autowired
    private lateinit var events: ApplicationEventPublisher

    //private var userClient:UserClient

    val allRetroBoards: Iterable<RetroBoard>
        get() = retroBoardRepository.findAll()

    // Uncomment this to see the effect of the @Observed annotation (Custom Observation))
    //@Observed(name = "retro-board-id",contextualName = "findRetroBoardById")
    fun findRetroBoardById(uuid: UUID): RetroBoard {
        return retroBoardRepository.findById(uuid)
            .orElseThrow { RetroBoardNotFoundException() }
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

    // Error
    /*
    @Async
    @EventListener
    fun newSavedUser(user:User){
        LOG.info("New user saved: {} {}",user.email, LocalDateTime.now())
    }
    */

    // Uncomment this to see the effect of the @TransactionalEventListener annotation - This is Spring Modulith way of doing it
    @Async
    @TransactionalEventListener
    fun newSavedUser(userEvent: UserEvent) {
        LOG.info("New user saved: {} {} {}", userEvent.email, userEvent.action, LocalDateTime.now())
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(RetroBoardAndCardService::class.java)
    }
}
