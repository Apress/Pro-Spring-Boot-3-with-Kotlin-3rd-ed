package com.apress.myretro.board

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document
data class RetroBoard(
    @Id @get:NotNull var retroBoardId: UUID? = null,

    @get:NotBlank(message = "A name must be provided")
    @get:NotNull
    var name: String? = null,

    var cards: MutableList<Card>? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @get:NotNull var created: LocalDateTime? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @get:NotNull var modified: LocalDateTime? = null
)
