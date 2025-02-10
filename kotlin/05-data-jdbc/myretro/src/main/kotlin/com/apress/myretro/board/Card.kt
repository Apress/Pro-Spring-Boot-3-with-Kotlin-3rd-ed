package com.apress.myretro.board

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table
data class Card(
    @Id
    var id: UUID? = null,

    @get:NotBlank
    var comment: String? = null,

    @get:NotNull
    var cardType:  CardType? = null,

    @get:JsonIgnore
    var retroBoardId: UUID? = null
)
