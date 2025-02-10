package com.apress.myretro.persistence

import com.apress.myretro.board.RetroBoard
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RetroBoardRepository : JpaRepository<RetroBoard, UUID>
