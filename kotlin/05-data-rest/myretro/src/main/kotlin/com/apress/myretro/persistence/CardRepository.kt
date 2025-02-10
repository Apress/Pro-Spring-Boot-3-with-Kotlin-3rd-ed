package com.apress.myretro.persistence

import com.apress.myretro.board.Card
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CardRepository : JpaRepository<Card, UUID>
