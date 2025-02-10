package com.apress.myretro.board

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.*

@Entity
data class Card(
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id val id: UUID? = null,

    @get:NotBlank
    var comment: String? = null,

    @Enumerated(EnumType.STRING)
    @get:NotNull
    var cardType: CardType? = null,

    @ManyToOne
    @JoinColumn(name = "retro_board_id")
    @JsonIgnore
    var retroBoard: RetroBoard? = null
)
