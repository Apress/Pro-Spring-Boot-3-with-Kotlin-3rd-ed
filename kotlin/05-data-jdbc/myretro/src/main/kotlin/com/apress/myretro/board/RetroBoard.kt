package com.apress.myretro.board

import jakarta.validation.constraints.NotBlank
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table
data class RetroBoard(
    @Id
    var id: UUID? = null,

    @get:NotBlank(message = "A name must be provided")
    var name:  String? = null,

    @MappedCollection(idColumn = "retro_board_id", keyColumn = "id")
    var cards: MutableMap<UUID, Card> = mutableMapOf()
)
