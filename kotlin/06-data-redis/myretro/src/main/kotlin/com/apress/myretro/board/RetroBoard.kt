package com.apress.myretro.board

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.util.*

@RedisHash("RETRO_BOARD")
data class RetroBoard(
    @Id
    @get:NotNull
    var id: UUID? = null,

    @get:NotBlank(message = "A name must be provided")
    var name: String? = null,

    var cards: MutableList<Card>? = null
)
