package com.apress.myretro.persistence

import com.apress.myretro.board.RetroBoard
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*

@RepositoryRestResource(path = "retros", itemResourceRel = "retros", collectionResourceRel = "retros")
interface RetroBoardRepository : JpaRepository<RetroBoard, UUID>
