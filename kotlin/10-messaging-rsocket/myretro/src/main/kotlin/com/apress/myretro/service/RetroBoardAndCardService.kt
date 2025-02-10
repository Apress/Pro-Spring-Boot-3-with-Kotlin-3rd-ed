package com.apress.myretro.service

import com.apress.myretro.board.RetroBoard
import com.apress.myretro.persistence.RetroBoardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class RetroBoardAndCardService {
    @Autowired
    private lateinit var retroBoardRepository: RetroBoardRepository

    fun findAllRetroBoards(): Flux<RetroBoard> =
        retroBoardRepository.findAll()

    fun findRetroBoardById(uuid: UUID): Mono<RetroBoard> =
        retroBoardRepository.findById(uuid)

    fun saveRetroBoard(retroBoard: RetroBoard): Mono<RetroBoard> =
        retroBoardRepository.save(retroBoard)

    fun deleteRetroBoardById(uuid: UUID): Mono<Void> =
        retroBoardRepository.deleteById(uuid).let {
            Mono.empty()
        }
}
