package com.apress.myretro.service

import com.apress.myretro.board.RetroBoard
import com.apress.myretro.events.RetroBoardEvent
import com.apress.myretro.events.RetroBoardEventAction
import com.apress.myretro.exceptions.RetroBoardNotFoundException
import com.apress.myretro.persistence.RetroBoardRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import java.util.function.Supplier

@Service
class RetroBoardAndCardService {
    private lateinit var retroBoardRepository: RetroBoardRepository

    private lateinit var eventPublisher: ApplicationEventPublisher

    val allRetroBoards: Iterable<RetroBoard>
        get() = retroBoardRepository.findAll()

    fun findRetroBoardById(uuid: UUID): RetroBoard {
        return retroBoardRepository.findById(uuid)
            .orElseThrow<RetroBoardNotFoundException>(Supplier<RetroBoardNotFoundException> { RetroBoardNotFoundException() })
    }

    fun saveOrUpdateRetroBoard(retroBoard: RetroBoard): RetroBoard {
        val retroBoardResult: RetroBoard = retroBoardRepository.save<RetroBoard>(retroBoard)
        eventPublisher.publishEvent(
            RetroBoardEvent(
                retroBoardResult.retroBoardId!!,
                RetroBoardEventAction.CHANGED,
                LocalDateTime.now()
            )
        )
        return retroBoardResult
    }

    fun deleteRetroBoardById(uuid: UUID) {
        retroBoardRepository.deleteById(uuid)
        eventPublisher.publishEvent(RetroBoardEvent(uuid, RetroBoardEventAction.DELETED, LocalDateTime.now()))
    }
}
