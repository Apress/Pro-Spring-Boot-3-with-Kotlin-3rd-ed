package com.apress.myretro.persistence

import com.apress.myretro.board.RetroBoard
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import java.util.*

interface RetroBoardRepository : ReactiveMongoRepository<RetroBoard, UUID>
