package com.apress.myretro.persistence

import com.apress.myretro.board.RetroBoard
import org.springframework.data.repository.CrudRepository
import java.util.*

interface RetroBoardRepository : CrudRepository<RetroBoard, UUID>
